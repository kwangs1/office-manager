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
					
					<svg class="info-icon" onclick="openMasterInfoModal(${cal.calMasterId})" 
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
	}
}