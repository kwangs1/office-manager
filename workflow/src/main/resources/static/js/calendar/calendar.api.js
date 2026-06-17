const CalendarAPI = {
	save: async (data) => {
		return await callApi("/api/calendars", 'POST', data);
	}
}