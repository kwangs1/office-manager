const ModalManager = {
	openDeptModal : function(mode) {
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
	},
	
	SignUpDeptModal: function(modalId) {
		this.openDeptModal('deptOnly');
		DeptTree.init('#jstree_demo_div', 'deptOnly', (data) => {
			const node = data.node;
		    $('#deptName').val(node.text);
		    $('#deptId').val(node.id);
		    this.close(modalId);
		});		
	},
	
	shareSelector: function() {
		CalendarShare.init();
		this.openDeptModal('withUser');

		const alreadySelectedIds = CalendarShare.selectedDepts.map(d => d.id);
		    
		DeptTree.init('#jstree_demo_div', 'withUser', (data) => {
			CalendarShare.handleDeptSelection(data);
		}, logindeptId, alreadySelectedIds);
	},
	
	open: function(modalId) {
		document.getElementById(modalId).classList.add('is-visible');
	},
	
	close: function(modalId) {
		const modal = document.getElementById(modalId);
		modal.classList.remove('is-visible');
		
		this.clearInputs(modal);
		
		if (typeof ModalResets !== 'undefined' && ModalResets[modalId]) {
			ModalResets[modalId]();
		}
	},
	
	clearInputs: function(modal) {
		modal.querySelectorAll('input, textarea').forEach(el => {
			if (el.type === 'checkbox') el.checked = false;
			else el.value = '';
		});
	}
}

