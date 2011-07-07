RecruitApp.views.Viewport = Ext.extend(Ext.Panel, {
    fullscreen: true,
    layout: {
    	type: 'card'
    },
    initComponent: function() {
        Ext.apply(this, {
            items: [
                { xtype: 'welcome', id: 'welcome' },
                { xtype: 'homecard', id: 'home' }
            ]
        });
        RecruitApp.views.Viewport.superclass.initComponent.apply(this, arguments);
    }
});