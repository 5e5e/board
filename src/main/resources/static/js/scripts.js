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
			var answerCountTemplate = $("#answerCountTemplate").html();
			var conutTemplate = answerCountTemplate
					.format(data.question.countOfAnswer);
			$(".qna-comment-count").remove();
			$(".qna-comment-slipp").prepend(conutTemplate);
		}
	});
}

$(".qna-comment-slipp-articles").on("click", ".link-delete-article", deleteAnswer);
function deleteAnswer(event) {
	event.preventDefault();
	console.log("bell");
	var deleteButton = $(this);
	var url = deleteButton.attr("href");
	console.log("url : " + url);
	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(request, error) {
			console.log(request);
			console.log(error);
		},
		success : function(data, request) {
			console.log(data);
			if (data.valid) {
				deleteButton.closest("article").remove();
				var answerCountTemplate = $("#answerCountTemplate").html();
				var conutTemplate = answerCountTemplate
						.format(data.countOfAnswer);
				$(".qna-comment-count").remove();
				$(".qna-comment-slipp").prepend(conutTemplate);
			}
		}
	});
}
String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};