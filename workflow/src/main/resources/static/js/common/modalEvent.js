// 1. 모달 여는 UI 전용 함수
function openDeptModal(mode) {
    $('#deptTreeModal').addClass('is-visible');
    if (mode === 'withUser') {
        $('#user_list_box').show();
        $('.tree-box').css('flex', '1'); 
    } else {
		$('#btn-send').hide();
        $('#user_list_box').hide();
		$('.tree-options').hide();
        $('.tree-box').css('flex', 'none').css('width', '100%');
    }
}

// 2. 회원가입용
function openSignUpDeptModal() {
    openDeptModal('deptOnly');
    DeptTree.init('#jstree_demo_div', 'deptOnly', (data) => {
 		const node = data.node;
        $('#deptName').val(node.text);
        $('#deptId').val(node.id);
        closeDeptModal();
    });
}

// 3. 달력 공유용
function shareSelector() {
    CalendarShare.init();
    openDeptModal('withUser');
	
	const alreadySelectedIds = CalendarShare.selectedDepts.map(d => d.id);
	    
	DeptTree.init('#jstree_demo_div', 'withUser', (data) => {
		CalendarShare.handleDeptSelection(data);
	}, logindeptId, alreadySelectedIds);
}

function closeDeptModal() {
    $('#deptTreeModal').removeClass('is-visible');
    
    const $tree = $('#jstree_demo_div');
    setTimeout(() => {
		if ($tree.hasClass('jstree')) $tree.jstree('destroy', true);
		$tree.removeClass('jstree jstree-default');
        
		$('#user_list_ul').empty();
		$('#checkAllUsers').prop('checked', false);
		$('.user-chk').prop('checked', false);
    }, 0);
}

// 캘린더 달력 등록 & 닫기
function openMasterSaveModal() {
	document.getElementById('MasterModal').classList.add('is-visible');
}

function closeMasterSaveModal() {
	document.getElementById('MasterModal').classList.remove('is-visible');
	document.getElementById('calName').value = '';

	const dynamicList = document.getElementById('dynamicShareList');
	if (dynamicList) dynamicList.innerHTML = '';

	const noMsg = document.getElementById('noShareMsg');
	if (noMsg) noMsg.style.display = 'inline';

	CalendarShare.selectedUsers = [];
	CalendarShare.selectedDepts = [];
}

// 일정 & 일정등록 상세보기 닫기
function closeEventModal() {
	document.getElementById('saveModal').classList.remove('is-visible');

	document.getElementById("title").value = "";
	document.getElementById("content").value = "";
	
	document.getElementById("allDay").checked = false;
	document.getElementById("startHours").disabled = false;
 	document.getElementById("startMin").disabled = false;
 	document.getElementById("endHours").disabled = false;
 	document.getElementById("endMin").disabled = false;
}

function closeInfoModal() {
	document.getElementById('detailModal').style.display = 'none';
}