RecruitApp.views.Homecard = Ext.extend(Ext.TabPanel, {
    title: "home",
    iconCls: "home",
    tabBarDock: 'bottom',
    initComponent: function() {
        Ext.apply(this, {
            defaults: {
                styleHtmlContent: true
            },
            items: [
            	RecruitApp.views.Homecard.quizView
            ,{
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
            },{
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
    	        	ui: 'action-round'
                }]
            }]
        });
        RecruitApp.views.Homecard.superclass.initComponent.apply(this, arguments);
    }
});

RecruitApp.views.Homecard.quizView = new Ext.Panel({
    	iconCls: 'home',
        title: 'Spill',
        scroll: 'vertical',
        layout: 'card',
        dockedItems: [{
        	xtype: 'toolbar',
        	title: 'Post 1'
        }],
        items: [{
        	items: [{
	        	html: '<p>Dette er en fin beskrivelse skrevet av en smart ansatt i HR for å fortelle en ivrig student hvor han kan finne den neste posten. Når studenten endelig finner posten kan han skrive inn den hemmelige kode i boksen under. Lykke til!!</p>'
	        },{
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
	        		RecruitApp.views.Homecard.quizView.setActiveItem(1);
	        	}
	        }]
        },
        {
        	items: [{
	        	html: '<p>Dette er et spøsrsmål som smart student må svare på slik at han kan få hint til neste post!! Det beste er om dette svaret er kort slik at student kan få vite om svar er riktig med en gang :)</p>'
	        },{
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
	        		RecruitApp.views.Homecard.quizView.setActiveItem(0);
	        	}
	        }]
        }]
});

RecruitApp.views.Homecard.findPost = new Ext.Panel({
	items: [{
		html: '<p>Dette er en fin beskrivelse skrevet av en smart ansatt i HR for å fortelle en ivrig student hvor han kan finne den neste posten. Når studenten endelig finner posten kan han skrive inn den hemmelige kode i boksen under. Lykke til!!</p>'
	},{
		xtype: 'numberfield',
		title: 'kode',
		label: 'Kode',
		required: true
	},{
		xtype: 'button',
		text: 'Levér kode',
		ui: 'action-round'
	}]
});

Ext.reg('homecard', RecruitApp.views.Homecard);