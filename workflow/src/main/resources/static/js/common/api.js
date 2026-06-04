/**
 * 공통 Fetch 함수
 * @Param {String} url - 요청주소
 * @Param {String} method - GET, POST, PUT, DELETE
 * @Param {Object} data - 전송할 데이터
 */
async function callApi(url, method, data){
	const options = {
		method: method,
		headers: {'Content-Type': 'application/json'}
	};
	
	if (data){
		options.body = JSON.stringify(data);
	}
	
	try{
		const response = await fetch(url, options);
		
		const res = await response.json();
		
		if (response.ok && res.success){
			return res;
		} else {
			alert(res.message);
			return null;
		}
	}catch (error){
		console.error("API Call Error:", error);
		alert(_MSG.NETWORK_ERROR);
		return null;
	}
}