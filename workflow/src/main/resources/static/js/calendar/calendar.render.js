const CalendarRender = {
	MasterRender : function($container, cals) {
		$container.empty();
		if (cals.length === 0) {
			$container.append('<li class="empty-msg">목록이 없습니다.</li>');
			return;
		}

		let typeLabel = '';
		const html = cals.map(cal => {
			if (cal.calType === 'DEPT') typeLabel = '[부서]';
			else if(cal.calType === 'PERSONAL') typeLabel = '[개인]';
			else typeLabel = '[공유]';
			
			return `
			<li class="master-item">
				<input type="checkbox" data-id="${cal.calMasterId}"> 
				<label class="master-label">
				    <span class="type-badge">${typeLabel}</span>
					<span class="cal-name">${cal.calName}</span>
					
					<svg class="info-icon" onclick="ModalManager.open('MasterInfoModal',${cal.calMasterId})" 
					    xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" 
						fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
						<circle cx="12" cy="12" r="10"></circle>
						<line x1="12" y1="16" x2="12" y2="12"></line>
						<line x1="12" y1="8" x2="12.01" y2="8"></line>
					</svg>
				</label>
			</li>			
			`}).join('');
		$container.append(html);		
	},
	
	MasterInfoRender : function(data, isEditMode = false) {
		$('#info-calName').val(data.calName).attr('readonly', true);
		$('#info-ownerName').val(data.ownerName);
		
		const shareBtnHtml = isEditMode ? `
			<button type="button" class="btn-share-add" onclick="ModalManager.shareSelector()">+ 공유자 추가</button>
		`: ``;

		$('#info-shareBtn').html(shareBtnHtml);
		const typeHtml  = isEditMode ? `
			<div class="radio-group">
			<label class="radio-label"><input type="radio" name="calType" value="PERSONAL" ${data.calType === 'PERSONAL' ? 'checked' : ''}/> 개인</label>
			<label class="radio-label"><input type="radio" name="calType" value="DEPT" ${data.calType === 'DEPT' ? 'checked' : ''}/> 부서</label>
			<label class="radio-label"><input type="radio" name="calType" value="COMPANY" ${data.calType === 'COMPANY' ? 'checked' : ''}/> 전사</label>
			</div>
		` :`
			<input type="text" value="${data.calType === 'DEPT' ? '부서달력' : '개인달력'}" readonly>
		`
		$('#info-calType-container').html(typeHtml);
		if (isEditMode) {
			$('#info-calName').val(data.calName).attr('readonly', false);
			$('#infoShareList').empty();
			
			CalendarShare.selectedDepts = data.shareList.filter(s => s.targetType === 'DEPT');
			CalendarShare.selectedUsers = data.shareList.filter(s => s.targetType === 'USER');
			
			CalendarShare.sendSelected();
		} else {
			const shareHtml = data.shareList.map(s => {

			let perms = [];
			if (s.permLevel & 1) perms.push('<span class="p-read">읽기</span>');
			if (s.permLevel & 2) perms.push('<span class="p-write">작성</span>');
			if (s.permLevel & 4) perms.push('<span class="p-modify">수정</span>');
			return `
			        <div style="padding: 8px; border-bottom: 1px solid #eee; display: flex; align-items: center;">
			            <strong style="min-width: 80px;">${s.targetName}</strong>
			            <div style="flex-grow: 1;">${perms.join(' ')}</div>
			        </div>
			    `;	
			}).join('');
			$('#infoShareList').html(shareHtml);
		}
		
		const footerHtml = isEditMode ? `
			<button type="button" class="btn-edit" onclick="CalendarService.update(${data.calMasterId},${JSON.stringify(data).replace(/"/g, '&quot;')})">저장</button>
			<button type="button" class="btn-cancel" onclick="ModalManager.close('MasterInfoModal')">닫기</button>
		` : `
			<button type="button" class="btn-edit" onclick="CalendarRender.handleEditMode(${JSON.stringify(data).replace(/"/g, '&quot;')})">수정</button>
			<button type="button" class="btn-del" onclick="deleteCalendar()">삭제</button>
			<button type="button" class="btn-cancel" onclick="ModalManager.close('MasterInfoModal')">닫기</button>
		`;
		$('#info-footer').html(footerHtml);
	},
	
	handleEditMode: function(data) {
		const isEditMode = true;
		CalendarShare.isEditMode = isEditMode;
		CalendarService.getInfo(data.calMasterId, isEditMode);
	}
}