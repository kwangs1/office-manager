const CalendarService = {
	save : async function() {
		const calNameInput = document.getElementById('calName');
		if (!calNameInput.checkValidity()) {
			calNameInput.reportValidity();
			return;
		}

		const data = this.collectCalendarData();
		const result = await CalendarAPI.save(data);

		if (result) {
			closeMasterSaveModal();
			alert("캘린더가 등록되었습니다.");
			this.initList();
		}		
	},
	
	getAuthData: function(row, prefix) {
		return {
			targetId: parseInt(row.getAttribute('data-id')),
			targetType: row.getAttribute('data-type'),
			read_auth: row.querySelector(`.${prefix}-read`)?.checked ? 'Y' : '',
			write_auth: row.querySelector(`.${prefix}-write`).checked ? 'Y' : '',
			modify_auth: row.querySelector(`.${prefix}-modify`).checked ? 'Y' : ''		
		}		
	},
	
	collectCalendarData: function() {
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
		    shareList.push(this.getAuthData(row, 'user'));
		},this);

		document.querySelectorAll('.share-dept-item').forEach(row => {
		    shareList.push(this.getAuthData(row, 'dept'));
		},this);

		return {
			calName: calNameInput.value.trim(),
			calType: calType,
			ownerId: finalOwnerId,
			deptId: finalDeptId,
			regId: loginUserId,
			shareList: shareList
		}		
	},
	
	initList: async function() {
		const res = await CalendarAPI.list();

		if (res && res.data) {
			const myCals = res.data.filter(cal => cal.calType === 'PERSONAL' && cal.ownerId === loginUserId);
			const deptCals = res.data.filter(cal => cal.calType === 'DEPT' && cal.deptId === logindeptId);
			const sharedCals = res.data.filter(cal => {
				if (cal.ownerId === loginUserId) return false;
				
				const isSharedToMe = cal.shareList.some(share => {
					if (share.targetType === 'USER') {
						return share.targetId === loginUserId;
					} else if (share.targetType === 'DEPT') {
						return share.targetId === logindeptId;
					}
					return false;
				});
				return isSharedToMe;
			});
			
			CalendarRender.MasterRender($('.master-dept-list'),deptCals);
			CalendarRender.MasterRender($('.master-list'),myCals);
			CalendarRender.MasterRender($('.share-list'),sharedCals);
		}		
	}
}