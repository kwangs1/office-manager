// select - option setting
function initSelectOptions(){
	const hourSelects = document.querySelectorAll('.hour-select');
	const minSelects = document.querySelectorAll('.min-select');

	// 시 옵션 생성 (00 ~ 23)
	hourSelects.forEach(select => {
		if(select.children.length > 0) return;
		for(let i =0; i <24; i++) {
			let hr = i < 10 ? '0' + i : i.toString();
			select.add(new Option(hr + "시", hr));
		}
	});
	// 분 옵션 생성 (00 ~ 59)
	minSelects.forEach(select => {
		if(select.children.length > 0) return;
		for(let i =0; i <60; i +=10) {
			let mn = i < 10 ? '0' + i : i.toString();
			select.add(new Option(mn + "분", mn));
		}
	});
}

// drap&drop, resize update date fomatting
function formatDate(date) {
	if (!date) return;
	
	const year = date.getFullYear();
	const month = String(date.getMonth() + 1).padStart(2, '0');
	const day = String(date.getDate()).padStart(2, '0');

	const hours = String(date.getHours()).padStart(2, '0');
	const minutes = String(date.getMinutes()).padStart(2, '0');
	const seconds = String(date.getSeconds()).padStart(2, '0');
	
	return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

// 폼 진입 시 시간, 분 셋팅
function modalDateSetting(start, end, isAllDay, startTime, endTime){
	var startHourStr, startMinStr, endHourStr, endMinStr;
	if (startTime && endTime) {
		startHourStr = startTime.split(':')[0];
		startMinStr = startTime.split(':')[1];
		endHourStr = endTime.split(':')[0];
		endMinStr = endTime.split(':')[1];
	} else {
		var now = new Date();
		var currentHour = now.getHours();
		var endHour = (currentHour + 1) %24;

		startHourStr = currentHour < 10 ? "0" + currentHour : currentHour;
		endHourStr = endHour < 10 ? "0" + endHour : endHour;
		startMinStr = "00";
		endMinStr = "00";
	}
	document.getElementById("start").value = start;
	document.getElementById("end").value = end;
	document.getElementById("startHours").value = startHourStr;
	document.getElementById("startMin").value = startMinStr;
	document.getElementById("endHours").value = endHourStr;
	document.getElementById("endMin").value = endMinStr;

	// 체크박스 상태 제어
	var allDayBox = document.getElementById('allDay');
	if (allDayBox) {
		allDayBox.checked = isAllDay;
		if (isAllDay) {
			handleAllDayChange(allDayBox, 'write');
		} else {
			const startH = document.getElementById("startHours");
			const startM = document.getElementById("startMin");
			const endH = document.getElementById("endHours");
			const endM = document.getElementById("endMin");
			if(startH) startH.disabled = false;
			if(startM) startM.disabled = false;
			if(endH) endH.disabled = false;
			if(endM) endM.disabled = false;
		}
	}
	document.getElementById('saveModal').classList.add('is-visible');
}

// 종일 체크 후 체크해제 시 쓰는 함수
function subtractOneDay(dateStr){
	var dateObj = new Date(dateStr);
	dateObj.setDate(dateObj.getDate() - 1);

	var year = dateObj.getFullYear();
	var month = String(dateObj.getMonth() + 1).padStart(2, '0');
	var day = String(dateObj.getDate()).padStart(2, '0');

	return `${year}-${month}-${day}`;
}

// 종일 체크 작성, 상세보기 모달 공통함수
function handleAllDayChange(checkbox, mode){
	const isChecked = checkbox.checked;

	const container = checkbox.closest('.modal-content') || checkbox.closest('.modal-info-content');
	const startH = container.querySelector('[id$="startHours"]') || container.querySelector('[id$="StartHours"]');
	const startM = container.querySelector('[id$="startMin"]') || container.querySelector('[id$="StartMin"]');
	const endH = container.querySelector('[id$="endHours"]') || container.querySelector('[id$="EndHours"]');
	const endM = container.querySelector('[id$="endMin"]') || container.querySelector('[id$="EndMin"]');

	const startDateInput = container.querySelector('[id$="start"]') || container.querySelector('[id$="Start"]');
	const endDateInput = container.querySelector('[id$="end"]') || container.querySelector('[id$="End"]');

	if (!startDateInput || !endDateInput) return;

	if (isChecked){
		var endDateObj = new Date(endDateInput.value);
		endDateObj.setDate(endDateObj.getDate() + 1);

		var year = endDateObj.getFullYear();
		var month = String(endDateObj.getMonth() + 1).padStart(2, '0');
		var day = String(endDateObj.getDate()).padStart(2, '0');

		var nextDatestr = year + "-" + month + "-" + day;
		endDateInput.value = nextDatestr;
		
		if(startH) { startH.disabled = true; startH.value = "00"; }
		if(startM) { startM.disabled = true; startM.value = "00"; }
		if(endH) { endH.disabled = true; endH.value = "00"; }
		if(endM) { endM.disabled = true; endM.value = "00"; }
	} else {
		const startDt = new Date(startDateInput.value);
		const endDt = new Date(endDateInput.value);

		const diffDays = Math.ceil(Math.abs(endDt - startDt) / (1000 * 60 * 60 * 24));
		const adjustedEndDate = subtractOneDay(endDt);
		endDateInput.value = diffDays <= 1 ? startDateInput.value : adjustedEndDate;

		if(startH) { startH.disabled = false; }
		if(endH) { endH.disabled = false; }
		if(startM) { startM.disabled = false; }
		if(endM) { endM.disabled = false; }
		
		if (mode === 'detail') {
			if(startH) { startH.value = checkbox.dataset.startH; }
			if(startM) { startM.value = checkbox.dataset.startM; }

			if(endH) { endH.value = checkbox.dataset.endH; }
			if(endM) { endM.value = checkbox.dataset.endM; }
		} else {
			var now = new Date();
			var currentHour = now.getHours();
			var endHour = currentHour + 1;

			var startHourStr = currentHour < 10 ? "0" + currentHour : currentHour;
			var endHourStr = endHour < 10 ? "0" + endHour : endHour;

			if(startH) { startH.value = startHourStr; }
			if(endH) { endH.value = endHourStr; }
		}
	}
}

// handleAllDayChange에 대한 이벤트 리스너
document.addEventListener('DOMContentLoaded', function() {
	const writeCheck = document.getElementById('allDay');
	if (writeCheck) {
		writeCheck.addEventListener('change', function() {
			handleAllDayChange(this, 'write');
		});
	}

	const detailCheck = document.getElementById('detailAllDay');
	if (detailCheck) {
		detailCheck.addEventListener('change', function() {
			handleAllDayChange(this, 'detail');
		});
	}
});

// 상세보기 모달 종일 값 여부에 따른 판단하여 시간 비활성화
function setupAllDayStatus(checkbox, times, allDayYn){
	if(!checkbox) return;

	const container = checkbox.closest('.modal-info-content');
	const hourSelects = container.querySelectorAll('.hour-select');
	const minSelects = container.querySelectorAll('.min-select');

	hourSelects.forEach(sel => sel.disabled = false);
	minSelects.forEach(sel => sel.disabled = false);

	checkbox.dataset.startH = times.startH;
	checkbox.dataset.startM = times.startM;
	checkbox.dataset.endH = times.endH;
	checkbox.dataset.endM = times.endM;

	checkbox.checked = (allDayYn === 'Y');
	if (allDayYn === 'Y') {
		handleAllDayChange(checkbox, 'detail');
	}
}