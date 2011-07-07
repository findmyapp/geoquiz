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
			numberOfPosts;
		
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
    		    			user = {
    		    				username: user,	
    		    				email: email_f,
    		    				postsCleared: 0
    		    			};
    		    			saveObject('user', user);
    		    			
    		    			// AJAX-call from jQuery (really ugly!!)
    		    			$.getJSON('event?eventId=0', function(data){
    		    				event = data;
    		    				saveObject('event', event);
    		    				numberOfPosts = event.questions.length;
    		    				setQuestion(1);
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
		        title: 'Skriv inn et brukernavn og epost:',
		        defaults: {
		            required: true,
		            labelAlign: 'center'
		        },
		        items: [{
		                xtype: 'textfield',
		                name: 'bruker',
		                label: 'Brukernavn',
		                value: (user!=null)?user.username:''
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
			title: 'Post 1'
		});
		
		var postDescription = new Ext.Panel({
			html: 'test'
		});
		
		var postQuestion = new Ext.Panel({
			html: 'test'
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
	        	xtype: 'button',
	        	text: 'Riktig svar, sender du til...',
	        	ui: 'action-round',
	        	// Handler for "Lever svar"-knapp
	        	handler: function(){
	        		var value = answerPostView.getComponent('svar').getValue().toLowerCase();
	        		if(hex_sha1(value)==question.answer){
	        			var nextPost = postNr + 1;
	        			// If there is more posts left, go to next
	        			if(nextPost<=numberOfPosts){
		        			setQuestion(nextPost);
		        			quizView.setActiveItem(0);
	        			}
	        			// else show "You're finished!"
	        			else {
	        				quizHeader.title = 'Hurra!';
	        				quizView.setActiveItem(2);
	        			}
	        			
	        		}
	        	}
	       }]
		});
		
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
			title: 'Ferdig!',
		    scroll: 'vertical',
		    dockedItems: [{
		    	xtype: 'toolbar',
		    	title: 'Avslutt'
		    }],
		    items: [{
		    		html: '<p>Jasså! Er du ferdig? Eller klarer du ikke mer... trykk på knappen under for å levere inn svarene dine :)</p>'
		        },{
		            xtype: 'button',
		    	    text: 'Ja, vi er ferdige!',
		    	    ui: 'action-round',
		    	    handler: function(){
		    	    	// TODO: handle submitting answers
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
		        statusView,
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
				postNr = postNum;
				question = event.questions[postNum-1];
				quizHeader.title = 'Post ' + postNum + '/' + numberOfPosts;
				postDescription.html = '<p style="font-weight: bold">Beskrivelse:</p><p>'+question.postDescription+'</p>';
				postQuestion.html = '<p style="font-weight: bold">Spørsmål:</p><p>'+question.question+'</p>';
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