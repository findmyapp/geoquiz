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
			
			// Load user from local storage
			user = loadObject('user');
			
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
    		    		var user = loginForm.getValues().bruker;
    		    		var email_f = loginForm.getValues().epost;    		    
    		    		
    		    		if(user.length>0 && validateEmail(email_f)){
    		    			welcomeView.setLoading(true);
    		    			
    		    			user = {
    		    				nickname: user,	
    		    				email: email_f,
    		    				eventId: 0 // This must be generic!
    		    			};
    		    			
    		    			saveObject('user', user);
    		    			
    		    			// AJAX-call from jQuery (really ugly!!)
    		    			//$.getJSON('event?eventId=0', function(data){
    		    			$.getJSON('createUser?user=' + JSON.stringify(user) , function(data){
    		    				event = data;
    		    				saveObject('event', event);
    		    				numberOfPosts = event.questions.length;
    		    				setQuestion(1);
    		    				welcomeView.setLoading(false);
    		    				mainView.setActiveItem(1);
    		    			});
    		    		}
    		    		// Illegal arguments in form
    		    		else {
    		    			alert('Ugyldig brukernavn/passord');
    		    		}
    		    	}
    			}]
    		});
		
		var loginForm = new Ext.form.FormPanel({
			items:[{
		        xtype: 'fieldset',
		        title: 'Skriv inn et kallenavn og epost:',
		        defaults: {
		            required: true,
		            labelAlign: 'center'
		        },
		        items: [{
		                xtype: 'textfield',
		                name: 'bruker',
		                label: 'Kallenavn',
		                value: (user!=null)?user.nickname:''
		            }, {
		                xtype: 'emailfield',
		                name: 'epost',
		                label: 'Epost',
		                value: (user!=null)?user.email:''
		        }]
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
	        			quizView.setActiveItem(1);
	        		}
	        		else {
	        			// Gi feilmelding!
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
	        			var nextPost = postNr + 1;
	        			
	        			// Add answer to answer list
	        			answers.push({
	        				id: question.id,
	        				answer: value
	        			});
	        			
	        			// If there is more posts left, go to next
	        			if(nextPost<=numberOfPosts){
		        			setQuestion(nextPost);
		        			quizView.setActiveItem(0);
	        			}
	        			// else show "You're finished!"
	        			else {
	        				goToFinish();
	        			}
	        			
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
		    	    	finishView.setLoading(true);
		    	    	//alert('respond?user='+JSON.stringify(user)+'&answers='+JSON.stringify(answers));
		    	    	$.get('respond?user='+JSON.stringify(user)+'&answers='+JSON.stringify(answers), function(data){
		    	    		alert('High performance. Delivered.');
		    	    		finishView.setLoading(false);
		    	    	});
		    	    }
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
				
				// Reset text fields
				answerPostView.getComponent('svar').setValue('');
				findPostView.getComponent('kode').setValue('');
				
				// Update title, description and question text
				quizHeader.setTitle('Post ' + postNum + '/' + numberOfPosts + ' (klart '+ answers.length +')');
				postDescription.update('<p style="font-weight: bold">Beskrivelse:</p><p>'+question.postDescription+'</p>');
				postQuestion.update('<p style="font-weight: bold">Sp&oslash;rsm&aring;l:</p><p>'+question.question+'</p>');
			
			}
		}
		
		/**##############################################
		 * TRASH CAN (will be deleted when this thing is finished)
		 ###############################################*/
		/*
		var operation = new Ext.data.Operation({
		    action: 'read',
		    eventId : 0
		});

		var eventAjax = new Ext.data.AjaxProxy({
		    url: '/geo-quiz/event'
		});
		
		// Data models
		Ext.regModel('User', {
			fields: [
			         'id',
			         'username',
			         'email',
			         'postsCleared'
			]
		});
		
		var userStore = new Ext.data.JsonStore({
			storeId: 'users',
		    proxy: {
		    	id  : 'user',
		    	model: 'User',
		    	type: 'localstorage',
				reader: {
					type: 'json',
					root: 'user'
				}
		    }
		});*/
	}
});