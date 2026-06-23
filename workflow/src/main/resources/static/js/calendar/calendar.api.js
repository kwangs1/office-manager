const CalendarAPI = {
	save: async (data) => {
		return await callApi("/api/calendars", "POST", data);
	},
	
	list: async () => {
		return await callApi("/api/calendars/list", "GET");
	},
	
	info: async (id) => {
		return await callApi(`/api/calendars/${id}`,"GET");
	}
}