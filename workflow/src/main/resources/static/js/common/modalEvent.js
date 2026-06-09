function fnDeptTree(mode = 'deptOnly', callback) {
   	$('#deptTreeModal').addClass('is-visible');
    
	if (mode === 'withUser') {
	        $('#user_list_box').show();
	        $('.tree-box').css('flex', '1'); 
	    } else {
	        $('#user_list_box').hide();
	        $('.tree-box').css('flex', 'none').css('width', '100%');
	    }

    DeptTree.init('#jstree_demo_div', mode, function(nodeId){
        const selectedNode = $('#jstree_demo_div').jstree(true).get_node(nodeId);
        
        if (mode === 'withUser') {
            fetchUserListByDept(nodeId);
        } else {
            if (callback) callback(selectedNode.id, selectedNode.text);
            closeDeptModal();
        }
    },100);
}

function closeDeptModal() {
	$('#deptTreeModal').removeClass('is-visible');
}

function openSignUpDeptModal() {
    fnDeptTree('deptOnly', (id, name) => {
        $('#deptName').val(name);
        $('#deptId').val(id);
    });
}