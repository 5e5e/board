$(".answer-write input[type=submit]").click(addAnswer);
function addAnswer(event) {
	event.preventDefault();
	var queryString = $(".answer-write").serialize();

	var url = $(".answer-write").attr("action");
	console.log("url : " + url);
	console.log("queryString : " + queryString);
	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : function(data) {
			console.log(data);
			console.log('failure');
		},
		success : function(data, status) {
			console.log(data);
			var answerTemplate = $("#answerTemplate").html();
			var template = answerTemplate.format(data.writer.name, data.time,
					data.contents, data.question.id, data.id);
			$(".qna-comment-slipp-articles").prepend(template);
			$("textarea[name=contents]").val("");
		}
	});
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};