/**
 * 팝업창 화면 중앙 띄우는 공통함수
 * @param {String} url - 이동할 페이지 경로
 * @param {String} name - 팝업창 이름
 * @param {Number} width - 팝업 너비
 * @param {Number} height - 팝업 높이
 */
function openPopupCenter(url, name, width, height){
	const browserLeft = window.screenX || window.screenLeft;
	const browserTop = window.screenY || window.screenTop;
	const browserWidth = window.outerWidth || document.documentElement.clientWidth;
	const browserHeight = window.outerHeight || document.documentElement.clientHeight;	
	
	const left = browserLeft + (browserWidth / 2) - (width / 2);
	const top = browserTop + (browserHeight / 2) - (height / 2);
	
	const options = `width=${width},height=${height},top=${top},left=${left},scrollbars=yes,resizable=yes`;
	
	return window.open(url, name, options);
}