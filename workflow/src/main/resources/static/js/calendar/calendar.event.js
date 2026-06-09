var calendar = "";
var tooltipEl = document.getElementById("calendarTooltip");

document.addEventListener('DOMContentLoaded', function() {
	initSelectOptions();
	var calendarOptions = {
		initialDate: new Date(),
		initialView: 'dayGridMonth',
		locale: 'ko',
		headerToolbar: {
			left: 'prev,next today',
			center: 'title',
			right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
		},
		nowIndicator: true,
		navLinks: true,
		selectable: true,
		selectMirror: true,
		dayMaxEvents: true,
		editable: true,
		/*eventSources: [
			{
				url:'/study/calendar/event',
				method: 'get',
				extraParams: {
					cmd : 'getEvents'
				},
				failure: function(){
					alert("일정을 불러오는 중 오류가 발생하였습니다.");
				},
				color: 'LightBlue',
				textColor: 'black'
			}
		],*/
		eventDataTransform: function(eventData){
			if (eventData.allDayYn === 'Y'){
				eventData.allDay = true;
			}else {
				eventData.allDay = false;
			}
			return eventData;
		},
		eventResize: function(info){
			const data = {
				id : info.event.id,
				start : formatDate(info.event.start),
				end : formatDate(info.event.end),
				title : info.event.title,
				content : info.event.extendedProps.content,
				allDay : info.event.allDay
			};
			if (!confirm("일정을 변경하시겠습니까?")){
				info.revert();
			} else {
				ResizeUpd(data);
			}
		},
		eventDrop: function(info){
			const data = {
				id : info.event.id,
				start : formatDate(info.event.start),
				end : formatDate(info.event.end),
				title : info.event.title,
				content : info.event.extendedProps.content,
				allDay : info.event.allDay
			}; 
			if (!confirm("일정을 변경하시겠습니까?")){
				info.revert();
			} else {
				dropUpd(data);
			}
		},
		eventClick: function(info){
			const calId = info.event.id;
			getInfo(calId);
		},
		dateClick: function(info){
			var clickedStr = info.dateStr;
			var currentView = info.view.type;

			if (clickedStr.indexOf('T') !== -1){
				var clickedDate = clickedStr.split('T')[0];
				var clickedTime = clickedStr.split('T')[1].substring(0, 5);

				var hour = parseInt(clickedTime.split(':')[0]);
				var min = parseInt(clickedTime.split(':')[1]);

				var endMin = min + 30;
				var endHour = hour;
				if (endMin >= 60) {
					endHour = hour + 1;
					endMin = 0;
				}
				var endHourStr = endHour < 10 ? "0" + endHour : endHour;
				var endMinStr = endMin < 10 ? "0" + endMin : endMin;
				var endTime = endHourStr + ":" + endMinStr;
				modalDateSetting(clickedDate, clickedDate, false, clickedTime, endTime);
			} else {
				if (currentView === 'dayGridMonth'){
					modalDateSetting(clickedStr, clickedStr, false);
				} else {
					modalDateSetting(clickedStr, clickedStr, true);
				}
			}
		},
		eventMouseEnter: function(info){
			var event = info.event;
			var title = event.title ? event.title : '제목 없음';
			var content = (event.extendedProps && event.extendedProps.content) ? event.extendedProps.content : '내용 없음';

			tooltipEl.innerHTML = `
			        <strong> 제목: </strong>${title}<br/>
			        <strong> 내용: </strong>${content}`;

			Object.assign(tooltipEl.style, {
				display: 'block',
			    top: `${info.jsEvent.pageY + 10}px`,
			    left: `${info.jsEvent.pageX + 10}px`
			});
		},
		eventMouseLeave: function(info){
			tooltipEl.style.display = 'none';
		},
		select: function(info){
			var start = info.startStr;
			var end = info.endStr;
			var currentView = info.view.type;

			if (info.allDay){
				var realEnd = subtractOneDay(end);
				if (currentView === 'dayGridMonth') {
					modalDateSetting(start, realEnd, false);
				} else {
					modalDateSetting(start, realEnd, true);
				}
			} else {
				var startDate = start.split('T')[0];
				var startTime = start.split('T')[1].substring(0, 5);

				var endDate = end.split('T')[0];
				var endTime = end.split('T')[1].substring(0, 5);

				modalDateSetting(startDate, endDate, false, startTime, endTime);
			}

			calendar.unselect();
		},
		eventContent: function(arg){
			var event = arg.event;
			var title = event.title ? event.title : '제목 없음';
			var content = (event.extendedProps && event.extendedProps.content) ? event.extendedProps.content : '내용 없음';
			var html = `
			        <div class="custom-event-box">
			            <div class="custom-event-title" style="font-weight: bold; color: #007bff; display: flex; align-items: center; gap: 4px;">
			                <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round">
			                    <path d="M19 21l-7-5-7 5V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2z"></path>
			                </svg>
			                ${title}
			            </div>
			            <div class="custom-event-desc" style="font-size: 11px; color: #666; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
			                ${content}
			            </div>
			        </div>`;
			return { html: html };
		}
	};

	calendar = new FullCalendar.Calendar(document.getElementById('calendar'), calendarOptions);
	calendar.render();
});
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
