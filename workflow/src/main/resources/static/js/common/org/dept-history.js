/**
 * dept-history api
 */

const DeptHistory = {
	init: function(){
		const urlParams = new URLSearchParams(window.location.search);
		const deptId = urlParams.get('deptId');	

		if (!deptId){
			alert(_MSG.INVALID_PARAM);
			window.close();
			return;
		}		
		this.load(deptId);
	},
	
	load: async function(deptId){
		const url = `/api/depts/${deptId}/histories`;
		const response = await callApi(url, 'GET');
		
		if (response){
			this.render(response.data);
		}
	},
	
	render: function(list){
		const target = $('#dept-History-container');

		if (!list || list.length === 0){
			target.html(`<tr><td colspan="4">${_MSG.LIST_LENGTH_EMPTY}</td></tr>`);
			return;
		}

		const html = list.map(item => `
			<tr>
				<td>${item.oldName}</td>
				<td>${item.newName}</td>
				<td>${item.updateReason}</td>
				<td>${item.updateDt}</td>
			<tr>
		`).join('');
		target.html(html);
	}
}