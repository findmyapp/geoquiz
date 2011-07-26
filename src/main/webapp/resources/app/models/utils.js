function validateEmail(elementValue){
	var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
	return emailPattern.test(elementValue);
}

function inArray(id, a){
	for(var i = 0; i < a.length; i++) {
		if(a[i].id === id){
			return true;
	    }
	  }
	  return false;
}
