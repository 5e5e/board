$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
	e.preventDefault();
	console.log("click!!");

	var queryString = $(".answer-write").serialize();
	console.log("query : " + queryString);

	var url = $(".answer-write").attr("action");
	console.log("url : " + url);

	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess
	});
}

function onError() {

}

function onSuccess(data, status) {
	console.log(data);
	var answerId = data.id;
	var answerWriter = data.writer.name;
	var answerCreateTime = "0000-00-00 00:00:00";
	var contents = data.contents;
	var questionId = data.question.id;
	var answerWriterId = data.writer.id;
	console.log(answerId);
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(answerWriter, answerCreateTime,
			contents, questionId, answerWriterId, answerId);
	$(".qna-comment-slipp-articles").prepend(template);

	$("textarea[name=contents]").val("");
}

$(".delete-answer-form button[type=submit]").click(deleteAnswer);

function deleteAnswer(e) {
	e.preventDefault();

	var deleteBtn = $(this);
	console.log(deleteBtn);
	var url = $(".delete-answer-form").attr("action");
	console.log("url : " + url);

	$.ajax({
		type : 'delete',
		url : url,
		dataType : 'json',
		error : function(xhr, status) {
			console.log("error");
		},
		success : function(data, status) {
			if (status) {
				deleteBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});
}
$(".update-answer-form button[type=submit]").click(updateAnswer);

function updateAnswer(e) {
	e.preventDefault();
	var template = document.querySelector(".answer-write").innerHTML;
	console.log(template);
	$(this).closest("article").append(template);

}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};