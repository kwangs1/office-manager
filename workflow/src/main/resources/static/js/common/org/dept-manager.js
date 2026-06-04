/**
 * dept api
 */
const DeptManager = {
	init: function(){
		DeptTree.init('#jstree_demo_div', (id) => this.viewDetail(id));
		this.bindEvents();
	},
	
	bindEvents: function(){
		$('#btnOpenAdd').on('click', () => this.openModal('ADD'));
		$('#btnOpenUpd').on('click', () => this.openModal('UPD'));
		$('#btnSave').on('click', () => this.executeSave());		
		$('#btnDel').on('click', () => this.executeDelete());
		$('.close-btn').on('click', () => this.closeModal());
		$('.btn-cancel').on('click', () => this.closeModal());
		
		$('#btnHistoryView').on('click', () =>{
			const deptId = $('#view_id').val();
			this.getDeptHistories(deptId);
		});
		
		
		$('#pop_name').on('input', function(e) {
		    const currentName = $(e.currentTarget).val();
		    const originalName = $('#view_name').val();
			

			if ($('#modalMode').val() == 'ADD') return;

		    if (currentName !== originalName) {
		        $('#reason-area').slideDown();
		    } else {
		        $('#reason-area').slideUp();
		    }
		});		
	},
	
	viewDetail: async function(id){
		const res = await callApi(`/api/org/depts/${id}`, 'GET');
		if (res){
			const info = res.data;

			$('#view_id').val(info.id);
			$('#view_name').val(info.name);
			$('#view_pid').val(info.pid);
			$('#view_depth').val(info.depth);
			$('#view_sort').val(info.SortOr);
		}
	},
	// 수정, 작성 Modal
	openModal: function(mode) {
		const id = $('#view_id').val();
		const name = $('#view_name').val();
		const parentId = $('#view_pid').val();

		if (mode === 'ADD') {
			$('#modalTitle').text(_MSG.ADD_TITLE);
			$('#pop_pid').val(id);
			$('#pop_parent_name').val(name);
			$('#pop_name').val('');
			$('#btnSave').text(_MSG.SAVE_BTN);
		} else {
			if(!id) return alert(_MSG.ID_REQUIRED);

			$('#modalTitle').text(_MSG.MODIFY_TITLE);
			$('#pop_pid').val(parentId);
			$('#pop_parent_name').val(_MSG.EDIT_READONLY);
			$('#pop_name').val(name);
			$('#btnSave').text(_MSG.MODIFY_BTN);
		}
		$('#modalMode').val(mode);
		$('#deptAddModal').css('display', 'flex');
		$('#reason-area').hide();
		$('#updateReason').val("");
	},
	closeModal: function() {
		$('#deptAddModal').css('display', 'none');
		$('#reason-area').hide();
		$('#updateReason').val("");
	},
	
	// 수정 & 작성
	executeSave: async function(){
		const mode = $('#modalMode').val();
		const id = $('#view_id').val();
		const updateReason = $('#updateReason').val();
		const currentName = $('#pop_name').val();
		const originalName = $('#view_name').val();

		if (mode === 'UPD' && currentName !== originalName) {
			if (!updateReason.trim()) {
				alert(_MSG.REASON_REQUIRED);
				$('#updateReason').focus();
				return;
			}
		}
		const data = {
			pid: $('#pop_pid').val(),
			name: $('#pop_name').val(),
			updateReason : updateReason
		};		
		const url = (mode === 'ADD') ? '/api/org/depts' : `/api/org/depts/${id}`;
		const method = (mode === 'ADD') ? 'POST' : 'PATCH';
		const res = await callApi(url, method, data);
		if (res) {
			alert(res.message);
			$('#deptAddModal').css('display', 'none');
			DeptTree.refresh();
		}
	},
	
	// 삭제
	executeDelete: async function(){
		const id = $('#view_id').val();
		if(!id) return alert(_MSG.ID_REQUIRED);
		
		const res = await callApi(`/api/org/depts/${id}/status`, 'PATCH');
		if(res){
			alert(res.message);
			DeptTree.refresh();
		}
	},
	
	getDeptHistories: function(deptId){
		const url = `/org/dept-histories/main?deptId=${deptId}`;
		openPopupCenter(url, 'dept-history', 790, 460);
	}
};