const DeptTree = {
	instance: null,
	
	init: function(elementId, mode, onSelectCallback, defaultNodeId = null, selectedIds = []){
		if ($(elementId).hasClass('jstree')) {
			$(elementId).jstree('destroy');
		}
		const plugins = (mode === 'withUser') ? ["wholerow", "checkbox"] : ["wholerow"];
		
		this.instance = $(elementId).jstree({
			'plugins' : plugins,
			'core':{
				'data':{
					'url': '/api/org/depts/tree',
					'dataFilter': (data) => this.filterData(data)
				}
			},
			'checkbox': {
				'three_state': false,
				'whole_node': false,
				'tie_selection': false
			}
        })
		.on("ready.jstree", () => {
			const inst = this.instance.jstree(true);
			
			const rootNodes = inst.get_json();
			if(rootNodes.length > 0) inst.open_node(rootNodes[0].id);
			
			if (selectedIds && selectedIds.length > 0) {
				selectedIds.forEach(id => inst.check_node(id));
			}		
			if (mode === 'withUser' && defaultNodeId) {
				inst.select_node(defaultNodeId);
				inst.open_node(defaultNodeId, () => {
				    inst.get_container().jstree('scroll_into_view', $('#' + defaultNodeId));
				});
			}
		})		
		.on("select_node.jstree check_node.jstree uncheck_node.jstree", (e, data) => {
            if (onSelectCallback) {
				onSelectCallback({ node: data.node, action: e.type });
			}
        })
	},
	
	//데이터 가공
	filterData: function(data){
		let json = JSON.parse(data);
		const convert = (list) => {
			$.each(list, (idx, item) => {
				item.text = item.name;
				if (item.children && item.children.length > 0){
					convert(item.children);
				} else {
					delete item.children;
					item.parent = (item.pid == null || item.pid == 0) ? "#" : item.pid;
				}
			});
		};
		convert(json);
		return JSON.stringify(json);
	},
	
	refresh: function(){
		if(this.instance) this.instance.jstree(true).refresh();
	}
}