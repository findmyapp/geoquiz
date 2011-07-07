function saveObject( key, obj){
	try {
		window.localStorage.removeItem( key );
		window.localStorage.setItem( key, JSON.stringify(obj) );
		//alert('User saved!');
	} catch(e) {
		alert( 'Failed to save object to local store' );
	}
}

function loadObject( key ){
	var user;
    try {
    	// Have to find another solution than using eval()!!!
    	user = eval('(' + window.localStorage.getItem( key ) + ')');
    }catch(e) {
    	user = null;
    }
    return user;
}