function addAnswer(e) {
	answerTemplate
	e.preventDefault();
	var html = document.querySelector('.answer-write .form-control');
	var key = html.getAttribute("name");
	var value = html.value;
	var query = key + "=" + value;
	console.log("message : " + html);
	console.log("key : " + key);
	console.log("value : " + value);
	console.log(typeof query, "query : " + query);
	var url = document.querySelector('.answer-write').getAttribute('action');
	console.log("url : " + url);
	// post 통신
	var xhr = new XMLHttpRequest();

	xhr.onload = function() {
		if (xhr.status === 200) {
			var answerTemplate = document.querySelector('#answerTemplate').innerHTML;
			jObjcet = JSON.parse(xhr.responseText);
			var template = answerTemplate.format(jObjcet.writer.name, 0,
					jObjcet.contents, jObjcet.question.id, jObjcet.id);
			$(".qna-comment-slipp-articles").prepend(template);
			document.querySelector('.answer-write textarea[name=contents]').value = '';
			alert('Something went wrong. Name is now ' + xhr.responseText);
		} else if (xhr.status !== 200) {
			alert('Request failed.  Returned status of ' + xhr.status);
		}
	};
	xhr.open('POST', url);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send(query);
}
var button = document.querySelector('.answer-write input[type=submit]');
button.addEventListener('click', addAnswer);

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};