/**
 * 上一页
 */
function toPrevious() {
  $("#handle").val("previous");
  if ($("#nowPage").val() == "1") {
    return false;
  }
  $("form:first").submit();
}

/**
 * 下一页
 */
function toNext() {
  $("#handle").val("next");
  if ($("#nowPage").val() == $("#totalSize").val()) {
    return false;
  }
  $("form:first").submit();
}

/**
 * 初期
 */
function showButton() {
  if ($("#nowPage").val() != "1") {
    $("#previous").addClass("canused");
  }

  if ($("#nowPage").val() != $("#totalSize").val()) {
    $("#next").addClass("canused");
  }
}