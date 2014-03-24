
try { 
	v_url = location.href;
	if (typeof(_SERVER) == 'undefined'){
		_SERVER=0;
	}
	if (v_url.indexOf('?')>=0) {
		v_url += "&server=" + _SERVER;
	}else{
		v_url += "?server=" + _SERVER;
	}
	if (typeof(_CAT) != 'undefined'){
		v_url += "&cat=" + _CAT;
	}
	
	v_get = '';
	if (typeof(tag_id) != 'undefined'){
		v_get = "&tag_id=" + tag_id;
	}
	v_url = escape(v_url);
	if (v_url != "" ) {
		document.write("<img src='http://thongke.24h.com.vn/24h-analytics/24h-analytics.php?rand="+Math.random()+v_get+"&amp;url_tracker=" + v_url + "' height='0' width='0'>");
	}
} catch (e) {}
