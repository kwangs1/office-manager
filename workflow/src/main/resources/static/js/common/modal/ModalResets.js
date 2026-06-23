/**
 * 공통 모달 함수 
 * 특정 모달 닫을 때 초기화
 */
const ModalResets = {
    deptTreeModal: () => {
		const $tree = $('#jstree_demo_div');

		setTimeout(() => {
		    if ($tree.hasClass('jstree')) $tree.jstree('destroy', true);
		    $tree.removeClass('jstree jstree-default');
		    
		    $('#user_list_ul').empty();
		    $('#checkAllUsers').prop('checked', false);
		}, 0);	
    },
	
    MasterModal: () => {
		const dynamicList = document.getElementById('dynamicShareList');
		if (dynamicList) dynamicList.innerHTML = '';

		const noMsg = document.getElementById('noShareMsg');
		if (noMsg) noMsg.style.display = 'inline';

		CalendarShare.selectedUsers = [];
		CalendarShare.selectedDepts = [];
    }
};