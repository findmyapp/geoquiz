Ext.setup({
	tabletStartupScreen: 'Default.png',
	icon: 'appIcon256.png',
	glossOnIcon: false,
	onReady: function() {
		
		// Global variables
		var user, 
			event,
			question,
			postNr,
			numberOfPosts,
			answers = [];
		
		/* INIT */
		var init = function() {
			
			// Load user, event and answers from local storage
			user = loadObject('user');
			event = loadObject('event');
			answers = loadObject('answers');
			postNr = parseInt( loadValue('postNr') );
			
			if(answers==null){
				answers = [];
			}
			
		}();
		
		/**#########################################
		 * WELCOME VIEW
		 ##########################################*/	
		var welcomeBar = new Ext.Toolbar({
    		dock: 'bottom',
    		title: 'Velkommen',
    		items: [
    		    {xtype:'spacer'}, 
    		    {
    		    	text: 'Start',
    		    	ui: 'action-round',
    		    	handler: function(){
    		    		
    		    		// If no network connection
    		    		if(!navigator.onLine){
    		    			setWelcomeFail('Finner ikke nettverkstilkobling...');
    		    			return;
    		    		}
    		    		
    		    		var nickname = nicknameField.getValue();
    		    		var email_f = emailField.getValue();   
    		    		var eventId = eventSelect.getValue();
    		    		
    		    		if(eventId==-1){
    		    			setWelcomeFail('Du har ikke valgt noen event!');
    		    			return;
    		    		}
    		    		
    		    		// Validate username and email
    		    		if(nickname.length>0 && validateEmail(email_f)){
    		    			welcomeView.setLoading(true);
    		    			
    		    			user = {
    		    				nickname: nickname,	
    		    				email: email_f,
    		    				eventId: eventId
    		    			};
    		    			
    		    			saveObject('user', user);

    		    			// AJAX-call from jQuery (really ugly!!)
    		    			var con = $.getJSON('createUser?user=' + JSON.stringify(user) , function(data){
    		    				event = data;
    		    				startEvent(event, 1); // Starte event med post nummer 1
    		    				
    		    				// remove error text
    		    				setWelcomeFail('');
    		    			});
    		    			
    		    			con.error(function(){
    		    				setWelcomeFail('Klarer ikke koble til nettverket...');
    		    			});
    		    		}
    		    		// Illegal arguments in form
    		    		else {
    		    			setWelcomeFail('Ugyldig kallenavn eller epost!');
    		    		}
    		    	}
    			}]
    		});
		
		function setWelcomeFail(text){
			welcomeView.setLoading(false);
			fail.update('<p>'+text+'</p>');
			welcomeView.add(fail);
			welcomeView.doLayout();
		}
		
		var nicknameField = new Ext.form.Text({
            name: 'bruker',
            label: 'Kallenavn',
            value: (user!=null)?user.nickname:''
        });
		
		var emailField = new Ext.form.Text({
			name: 'epost',
            label: 'Epost',
            value: (user!=null)?user.email:''
        });
		
		var eventSelect = new Ext.form.Select({
			name: 'epost',
            label: 'Event',
            options: [
                {text: 'Velg event',  value: -1}
            ]
		});
		
		var loginForm = new Ext.form.FormPanel({
			items:[{
		        xtype: 'fieldset',
		        title: 'Skriv inn et kallenavn og epost:',
		        defaults: {
		            required: true,
		            labelAlign: 'center'
		        },
		        items: [
		            nicknameField,
		            emailField,
		            eventSelect
		        ]
			}]
		});
		
		var welcomeView = new Ext.Panel({
			layout: {
				type: 'vbox',
				align: 'stretch',
				pack: 'center'
			},
			items: [loginForm],
			dockedItems: [welcomeBar]
		});
		
		/**#####################################################
		 *                  QUIZ VIEW
		 ######################################################*/
		var quizHeader = new Ext.Toolbar({
			title: 'Not started'
		});
		
		var postDescription = new Ext.Panel({
			padding: 10,
			html: 'Not started'
		});
		
		var postQuestion = new Ext.Panel({
			padding: 10,
			html: 'Not started'
		});
		
		var fail = new Ext.Panel({
			cls: 'error-text',
			padding: 10,
			html: 'Not started'
		});
		
		var findPostView = new Ext.Panel({
			padding: 10,
			items: [
			    postDescription,
			{
	        	id: 'kode',
			    xtype: 'textfield',
	        	title: 'kode',
	        	label: 'Kode',
	        	required: true
	        },{
	        	xtype: 'button',
	        	padding: 5,
	        	text: 'Vi har funnet posten!',
	        	ui: 'action-round',
	        	// handler for "Funnet post"-knapp
	        	handler: function(){
	        		var value = findPostView.getComponent('kode').getValue().toLowerCase();
	        		if(hex_sha1(value)==question.activationCode){
	        			fail.update('');
	        			quizView.setActiveItem(1);
	        		}
	        		else {
	        			// Gi feilmelding!
	        			fail.update('<p>Feil kode!</p>');
	        			findPostView.insert(3, fail);
	        			findPostView.doLayout();
	        		}
	        	}
	        },{
	        	xtype: 'button',
	        	cls: 'decline-button',
	        	text: 'Jeg gir opp! Neste post...',
	        	ui: 'decline-round',
	        	// Handler for "Lever svar"-knapp
	        	handler: function(){
	        		var nextPost = postNr + 1;
        			
	        		fail.update('');
	        		
        			// If there is more posts left, go to next
        			if(nextPost<=numberOfPosts){
	        			setQuestion(nextPost);
        			}
        			else{
        				goToFinish();
        			}
	        	}
	       },{
	    	   html: '<p>Kan ikke angres!!</p>',
	    	   cls: 'warning-text'
	       }]
		});
		
		var answerPostView = new Ext.Panel({
			padding: 10,
			items: [
			    postQuestion,
			{
	        	id: 'svar',
			    xtype: 'textfield',
	        	title: 'svar',
	        	label: 'Svar',
	        	required: true
	        },{
	        	padding: 5,
	        	xtype: 'button',
	        	text: 'Send inn svar',
	        	ui: 'action-round',
	        	// Handler for "Lever svar"-knapp
	        	handler: function(){
	        		var value = answerPostView.getComponent('svar').getValue().toLowerCase();
	        		
	        		// If correct answer
	        		if(hex_sha1(value)==question.answer){
	        			var nextPost = (postNr + 1);
	        			
	        			// Add answer to answer list (if not exist)
	        			if(!inArray(question.id, answers)){
	        				answers.push({
		        				id: question.id,
		        				answer: value
		        			});
	        			}
	        			
	        			saveObject('answers', answers);
	        			fail.update('');
	        			
	        			// If there is more posts left, go to next
	        			if(nextPost<=numberOfPosts){
		        			setQuestion(nextPost);
		        			quizView.setActiveItem(0);
	        			}
	        			// else show "You're finished!"
	        			else {
	        				saveValue('postNr', nextPost);
	        				goToFinish();
	        			}	
	        		}
	        		else {
	        			fail.update('<p>Feil svar!</p>');
	        			answerPostView.insert(3, fail);
	        			answerPostView.doLayout();
	        		}
	        	}
	       },{
	        	xtype: 'button',
	        	cls: 'decline-button',
	        	text: 'Hopp til neste post',
	        	ui: 'decline-round',
	        	// Handler for "Lever svar"-knapp
	        	handler: function(){
	        		var nextPost = postNr + 1;
        			
	        		fail.update('');
	        		
        			// If there is more posts left, go to next
        			if(nextPost<=numberOfPosts){
	        			setQuestion(nextPost);
	        			quizView.setActiveItem(0);
        			}
        			else{
        				goToFinish();
        			}	
	        	}
	       },{
	    	   html: '<p>Kan ikke angres!!</p>',
	    	   cls: 'warning-text'
	       }]
		});
		
		function goToFinish(){
			quizHeader.setTitle('Hurra!');
			quizView.setActiveItem(2);
		}
		
		var youAreFinishedView = new Ext.Panel({
			padding: 10,
			items: [{
				html: '<p>Hurra! Du er ferdig!</p>'
	        }]
		});
		
		var quizView = new Ext.Panel({
		    	iconCls: 'home',
		        title: 'Spill',
		        scroll: 'vertical',
		        layout: 'card',
		        dockedItems: [
		            quizHeader
		        ],
	        	items: [
	        	        findPostView,
	        	        answerPostView,
	        	        youAreFinishedView
	        	]
		});
		
		/**#####################################################
		 *                  STATUS VIEW
		 ######################################################*/
		var statusView = new Ext.Panel({
            iconCls: 'organize',
            title: 'Status',
            scroll: 'vertical',
            dockedItems: [{
            	xtype: 'toolbar',
            	title: 'Status'
            }],
            items: [{
            	html: '<p>Her vil man finne en oversikt over poster man har klart! (Og ikke klart...)</p>'
            }]
        });
		
		/**#####################################################
		 *                 FINISH VIEW
		 ######################################################*/
		function setFinishFail(text){
			finishView.setLoading(false);
			fail.update('<p>'+text+'</p>');
			finishView.insert( 2, fail);
			finishView.doLayout();
		}
		
		var finishView = new Ext.Panel({
			iconCls: 'action',
			title: 'Send svar',
		    scroll: 'vertical',
		    dockedItems: [{
		    	xtype: 'toolbar',
		    	title: 'Svarsiden'
		    }],
		    items: [{
		    		padding: 10,
		    		html: '<p>Ved &aring; trykke p&aring; knappen under kan du levere inn svarene for de postene du har klart. Du kan levere inn svar s&aring; mange ganger du vil!</p>'
		        },{
		            padding: 10,
		            margin: 10,
		        	xtype: 'button',
		    	    text: 'Send inn svar!',
		    	    ui: 'action-round',
		    	    handler: function(){
		    	    	
		    	    	// If no network connection
    		    		if(!navigator.onLine){
    		    			setFinishFail('Finner ikke nettverkstilkobling...');
    		    			return;
    		    		}
		    	    	
    		    		// Start loader
    		    		finishView.setLoading(true);
    		    		
		    	    	var con = $.get('respond?user='+JSON.stringify(user)+'&answers='+JSON.stringify(answers), function(data){
		    	    		setFinishFail('<span style="color: #228b22">High performance. Delivered.</span>');
		    	    		finishView.setLoading(false);
		    	    	});
		    	    	con.error(function(){
		    	    		setFinishFail('Problemer med nettverkstilkoblingen...');
		    	    	});
		    	    }
		    },{
	        	xtype: 'button',
	        	cls: 'decline-button',
	        	text: 'Avslutt',
	        	ui: 'decline-round',
	        	// Handler for "Avslutt"-knapp
	        	handler: function(){
	        		clearEvent();
	        		quiz.setActiveItem(0);
	        		loadEventsList();
	        		mainView.setActiveItem(0);
	        	}
	       },{
	    	   html: '<p>Husk &aring; levere inn svar f&oslash;r du avslutter!!</p>',
	    	   cls: 'warning-text'
	       }]
		});
		
		/**#####################################################
		 *                  WRAPPERS
		 ######################################################*/
		var quiz = new Ext.TabPanel({
			title: "home",
			iconCls: "home",
			tabBarDock: 'bottom',
			items: [
			    quizView,
		        //statusView,
		        finishView
		    ]
		});
		
		// Main view
		var mainView = new Ext.Panel({
		    fullscreen: true,
		    layout: {
		    	type: 'card'
		    },
		    items: [
		        welcomeView,
		        quiz
		    ]
		});
		/**##############################################
		 *               OTHER
		 ###############################################*/
		
		function setQuestion(postNum){
			if(numberOfPosts>=postNum){
				
				// Retrieve next question
				postNr = postNum;
				question = event.questions[postNum-1];
				
				// Save postNr to local storage
				saveValue('postNr', postNr);
				
				// Reset text fields
				answerPostView.getComponent('svar').setValue('');
				findPostView.getComponent('kode').setValue('');
				
				// Update title, description and question text
				quizHeader.setTitle('Post ' + postNum + '/' + numberOfPosts + ' (klart '+ answers.length +')');
				postDescription.update('<p style="font-weight: bold">Beskrivelse:</p><p>'+question.postDescription+'</p>');
				postQuestion.update('<p style="font-weight: bold">Sp&oslash;rsm&aring;l:</p><p>'+question.question+'</p>');
			
			}
			else {
				mainView.setActiveItem(1);
				goToFinish();
			}
		}
		
		function startEvent(event, postNr){
			saveObject('event', event);
			numberOfPosts = event.questions.length;
			setQuestion(postNr);
			welcomeView.setLoading(false);
			mainView.setActiveItem(1);
		}
		
		function clearEvent(){
			event = null;
			deleteObject('event');
			answers = [];
			deleteObject('answers');
			postNr = 1;
			deleteValue('postNr');
		}
		
		function loadEventsList(){
			$.getJSON('events',function(data){
				var res = [];
				for(var i = 0; i<data.length; i++){
					res.push({
						text: data[i].title,  
						value: data[i].id
					});
				}
				eventSelect.setOptions(res, false);
				if(data.length>0)
					eventSelect.setValue(data[0].id);
			});
		}
		
		// Auto-load old event
		if(event!=null && user!=null){
			startEvent(event, postNr);
		}
		// Download list with open events
		else {
			loadEventsList();
		}
	}
});