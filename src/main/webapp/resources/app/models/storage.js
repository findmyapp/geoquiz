function saveObject( key, obj){
	try {
		window.localStorage.removeItem( key );
		window.localStorage.setItem( key, JSON.stringify(obj) );
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

function deleteObject( key ){
	try {
		window.localStorage.removeItem( key );
	} catch(e) {
		alert( 'Failed to delete object from local store' );
	}
}

function saveValue( key, value){
	try {
		window.localStorage.removeItem( key );
		window.localStorage.setItem( key, value );
	} catch(e) {
		alert( 'Failed to save value to local store' );
	}
}

function loadValue( key ){
	var value;
    try {
    	value =  window.localStorage.getItem( key );
    }catch(e) {
    	value = 1;
    }
    return value;
}

function deleteValue( key ){
	try {
		window.localStorage.removeItem( key );
	} catch(e) {
		alert( 'Failed to delete value from local store' );
	}
}