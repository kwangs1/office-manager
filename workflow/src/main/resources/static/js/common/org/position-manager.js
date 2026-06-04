/**
 * position crud js
 */
const PositionManager = {
	init : function(){
		this.view();
		this.bindEvents();
	},
	
	bindEvents: function(){
		$('.close').on('click', () => this.closeModal());
		$('#closeModal').on('click', () => this.closeModal());
		$('#savePosition').on('click', () => this.savePosition());
		$('#openAddModal').on('click', () => this.openAddModal());
		
		$('#position-list-container').off('click').on('click','.btn-edit', (e) => { // 수정버튼
			const item = $(e.currentTarget).data('item');
			this.openEditModal(item);
		}).on('click','.btn-danger', (e) => { // 삭제버튼
			const id = $(e.currentTarget).data('id');
			this.deletePosition(id);
		});		
	},
	
	view : async function() {
		const res = await callApi('/api/org/positions', 'GET');
		if (res){
			this.renderPositionList(res.data);
		};		
	},
	
	renderPositionList: function(list){
		if (!list || list.length == 0){
			$('#position-list-container').html(`<tr><td colspan="2">${_MSG.LIST_LENGTH_EMPTY}</td></tr>`);
			return;
		}
				
		const rows = list.map(item => `
			<tr>
				<td>${item.name}</td>
				<td>${item.secLevel}</td>
				<td>
					<button class='btn btn-edit' data-item='${JSON.stringify(item)}'>${_MSG.MODIFY_BTN}</button>
					<button class='btn btn-danger' data-id='${item.id}'>${_MSG.DELETE_BTN}</button>
				</td>
			</tr>
		`).join('');
			
		$('#position-list-container').html(rows);
},
	
	openAddModal: function(){
		$('#modalTitle').text(_MSG.ADD_TITLE);
		$('#id').val('');
		$('#name').val('');
		$('#secLevel').val('');
		$('#positionModal').show();		
	},
	openEditModal: function(item){
		$('#modalTitle').text(_MSG.MODIFY_TITLE);
		$('#id').val(item.id); 
		$('#name').val(item.name);
		$('#secLevel').val(item.secLevel);
		$('#positionModal').show();		
	},
	closeModal: function(){
		$('#positionModal').hide();
	},
	
	savePosition : async function(){
		const id = $('#id').val();
		const data = {
				name : $('#name').val(),
				secLevel: $('#secLevel').val()
		};

		const method = id ? 'PATCH' : 'POST';
		const url = id ? `/api/org/positions/${id}` : '/api/org/positions';

		const res = await callApi(url, method, data);
		if (res){
			alert(res.message);
			this.closeModal();
			this.view();
		}		
	},
	
	deletePosition: async function(id){
		if (!confirm(_MSG.DEL_CONFIRM)) return;

		const res = await callApi(`/api/org/positions/${id}`, 'DELETE');
		if (res){
			alert(res.message);
			this.view();
		}
	}
}