/**
 * dept tree
 */
const DeptTree = {
	instance: null,
	
	init: function(elementId, mode, onSelectCallback){
		if ($(elementId).hasClass('jstree')) {
			$(elementId).jstree('destroy');
		}
		const plugins = ["wholerow"];
		if (mode === 'withUser') plugins.push("checkbox");
		
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
				'whole_node': false
			}
        }).on("select_node.jstree check_node.jstree uncheck_node.jstree", (e, data) => {
            if (onSelectCallback) onSelectCallback(data.node.id);
        });
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