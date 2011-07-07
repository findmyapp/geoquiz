RecruitApp.views.Welcome = Ext.extend(Ext.Panel, {
    fullscreen: true,
	title: "welcome",
    iconCls: "home",
    initComponent: function() {
        Ext.apply(this, {
            defaults: {
                styleHtmlcontent: true
            },
            layout: {
            	type: 'vbox',
            	pack: 'center'
            },
            dockedItems: [{
        		xtype: 'toolbar',
        		dock: 'bottom',
        		title: 'Velkommen',
        		items: [
        		    {xtype:'spacer'}, 
        		    {
        		    	text: 'Start',
        		    	ui: 'action-round',
        		    	handler: function(){
        		    		RecruitApp.views.viewport.setActiveItem(1);
        		    	}
        			}]
        	}],
            items: [RecruitApp.views.Welcome.form]
        });
        RecruitApp.views.Welcome.superclass.initComponent.apply(this, arguments);
    }
});

RecruitApp.views.Welcome.form = new Ext.form.FormPanel({
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
                label: 'Brukernavn'
            }, {
                xtype: 'emailfield',
                name: 'epost',
                label: 'Epost'
        }]
	}]
});

Ext.reg('welcome', RecruitApp.views.Welcome);