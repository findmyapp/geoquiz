function getPage(url){
	$.getJSON(url, function(data){
		return data;
	});
}