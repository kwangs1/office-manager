async function saveCalendarMaster() {
	const calNameInput = document.getElementById('calName');
	if (!calNameInput.checkValidity()) {
		calNameInput.reportValidity();
		return;
	}
	
	const data = collectCalendarData();
	const result = await CalendarAPI.save(data);
	
	if (result) {
		closeMasterSaveModal();
		alert("캘린더가 등록되었습니다.");
	}
}
function getAuthData(row, prefix) {
	return {
		targetId: parseInt(row.getAttribute('data-id')),
		targetType: row.getAttribute('data-type'),
		read_auth: row.querySelector(`.${prefix}-read`)?.checked ? 'Y' : '',
		write_auth: row.querySelector(`.${prefix}-write`).checked ? 'Y' : '',
		modify_auth: row.querySelector(`.${prefix}-modify`).checked ? 'Y' : ''		
	}
}
function collectCalendarData() {
	const calNameInput = document.getElementById('calName');
	const calType = document.querySelector('input[name="calType"]:checked')?.value;
	let finalOwnerId = null;
	let finalDeptId = null;

	if (calType === 'PERSONAL') {
		finalOwnerId = loginUserId;
	} else {
		finalDeptId = logindeptId;
	}

	const shareList = [];

	document.querySelectorAll('.share-user-item').forEach(row => {
	    shareList.push(getAuthData(row, 'user'));
	});

	document.querySelectorAll('.share-dept-item').forEach(row => {
	    shareList.push(getAuthData(row, 'dept'));
	});
	
	return {
		calName: calNameInput.value.trim(),
		calType: calType,
		ownerId: finalOwnerId,
		deptId: finalDeptId,
		regId: loginUserId,
		shareList: shareList
	}
}