var playlist;

function playPassage(startVerseId, endVerseId) {
    $("#playerHolder").html('<div id="jPlayer" class="jp-jplayer"></div><div id="jp_container_1"><div class="jp-playlist"><ul></ul></div></div>');
    var params = 'startVerseId=' + startVerseId.toString() + '&endVerseId=' + endVerseId.toString();
    $.getJSON('/ajax/passageAudio.xhtml', params, function(json) {
        playPassage2(json);
        $('#listen_' + startVerseId.toString() + '_' + endVerseId.toString()).html('<a href="javascript:stopPassage(' + startVerseId.toString() + ',' + endVerseId.toString() + ');"><img src="/images/stop.gif" border="0" alt="Stop"></a> ');
    });
}

function stopPassage(startVerseId, endVerseId) {
    $("#jPlayer").jPlayer("stop");
    initListenElement($('#listen_' + startVerseId.toString() + '_' + endVerseId.toString())[0]);
}

function playPassage2(fileNames) {
    playlist = new jPlayerPlaylist(
        { jPlayer: "#jPlayer" },
        fileNames,
        {
            swfPath: "/js/jQuery.jPlayer.2.1.0",
            //swfPath: "http://jplayer.org/latest/js",
            supplied: "mp3",
            wmode: "window",
            playlistOptions: { autoPlay: true },
            ended: function(event) { initListen(); },
            ready: function(event) {
                //alert(fileNames[0]);
                //playlist.setPlaylist(fileNames);
                //playlist.select(0);
                //$("#jPlayer").jPlayer("play");
            },
            nativeSupport: false
        }
    );
}


function vote(contentType, contentId, up) {
    var params = 'contentType=' + contentType + '&contentId=' + contentId.toString();
    if (up) params = params + '&up=1'; else params += '&up=0';
    var elementName = 'v_' + contentType + '_' + contentId.toString();
    $.get('/ajax/vote.xhtml', params, function(data) {
        $('#' + elementName).html(data);
        initVotingElement($('#' + elementName)[0]);
    });
}

function loadComment(el) {
    
    parts = el.parentNode.id.split('_');
    var params = 'contentType=' + parts[1] + '&contentId=' + parts[2].toString() + "&parentId=" + parts[3];
    $.get('/ajax/comment.xhtml', params, function(data) {
        //alert('loaded comment');
        el.parentNode.innerHTML = data;
        loadWysiwyg();
    });
    return false;
}

function loadWysiwyg() {
    $('.commentText').wysiwyg({
        initialContent: '',
        controls: {
            strikeThrough: { visible: false },
            subscript: { visible: false },
            superscript: { visible: false },
            justifyLeft: { visible: false },
            justifyCenter: { visible: false },
            justifyRight: { visible: false },
            justifyFull: { visible: false },
            indent: { visible: false },
            outdent: { visible: false },
            undo: { visible: false },
            redo: { visible: false },
            insertOrderedList: { visible: false },
            insertUnorderedList: { visible: false },
            insertTable: { visible:false },
            insertHorizontalRule: {visible: false},
            insertImage: { visible: false },
            code: { visible: false },
            h1: { visible: false },
            h2: { visible: false },
            h3: { visible: false }
        }
    });
}


function submitComment(el) {
    container = el.parentNode.parentNode;
    parts = container.id.split('_');
    var params = 'contentType=' + parts[1] + '&contentId=' + parts[2].toString() + "&parentId=" + parts[3];
    body = document.getElementById('commentText_' + parts[1] + '_' + parts[2] + '_' + parts[3]).value;
    params += '&body=' + escape(body);
   
    //do a post
    $.get('/ajax/comment.xhtml', params, function(data) {
        //el.parentNode.innerHTML = data;
        showAllComments(parts[1], parts[2]);
    });

    
    //container.innerHTML = '<span class="commentHolder" id="comment_' + parts[1] + '_' + parts[2] + '_' + parts[3] + "\"></span>";
    
    return false;
}


function showOriginalComment(commentId) {
    var params = 'id=' + commentId.toString();
    loadModal('/modal/originalPost.xhtml?' + params, 'Original Comment', 600, 400);
}

function moderateComment(commentId) {
    var params = 'commentId=' + commentId.toString();
    loadModal('/modal/moderateComment.xhtml?' + params, 'Edit Comment', 600, 400);
}

function submitRelatedPassage(contentType,contentId) {
    var params = 'contenttype=' + contentType + '&contentId=' + contentId.toString();
    loadModal('/modal/submitPassage.xhtml?' + params, 'Submit Passage', 600,400);
}


function submitRelatedTopic(contentType, contentId) {
    var params = 'contenttype=' + contentType + '&contentId=' + contentId.toString();
    loadModal('/modal/submitTopic.xhtml?' + params, 'Submit Topic', 600, 100);
}


function showRegister() {
    loadModal('/modal/register.xhtml', 'Register', 400,200);
}

function loadModal(url, title, width,height) {
    var newUrl = '/modal/frame.xhtml';
    var newParams = 'height=' + height.toString() + '&url=' + escape(url);
    
    if ($('#modalHolder').length == 0) {
        $('#footer').append('<div id="modalHolder"></div>');
    }
    $.get(newUrl, newParams, function(data) {
        $('#modalHolder').html(data);
        $('#modalHolder').dialog(
            { modal: true, width: width, title: title }
        );
    });
}


function logout() {
    $.get('/ajax/login.xhtml', 'logout=1', function(data) {
        window.location.reload();
    });
}

function login() {
    var params='email=' + $('#email-field').val() + '&password=' + $('#password-field').val();
    $.get('/ajax/login.xhtml', params, function(data) {
        if (data == 'true') {
            window.location.reload();
        } else {
            loadModal('/modal/forgotPassword.xhtml', 'Forgot Password', 400, 200);
        }
    });
    return false;
}

function search() {
    location.href = '/search.xhtml?q=' + escape($('#search-field').val());
    return false;
}

function changeTranslation() {
    var params = 'id=' + $('#select-field').val();
    $.get('/ajax/translation.xhtml', params, function(data) {
        window.location.reload();
    });
    return false;
}

function showAllRelatedPassages(contentType, contentId) {
    var params = 'contentType=' + contentType + '&contentId=' + contentId.toString();
    $.get('/ajax/relatedPassages.xhtml', params, function(data) {
        $('#relatedPassages').html(data);
        initVoting();
    });
}


function showAllRelatedTopics(contentType, contentId) {
    var params = 'contentType=' + contentType + '&contentId=' + contentId.toString();
    $.get('/ajax/relatedTopics.xhtml', params, function(data) {
        $('#relatedTopics').html(data);
        initVoting();
    });
}

function showAllRelatedDiscussions(contentType, contentId) {
    var params = 'contentType=' + contentType + '&contentId=' + contentId.toString();
    $.get('/ajax/relatedDiscussions.xhtml', params, function(data) {
        $('#relatedDiscussions').html(data);
    });
}


function showAllComments(contentType, contentId) {
    var params = 'contentType=' + contentType + '&contentId=' + contentId.toString();
    $.get('/ajax/comments.xhtml', params, function(data) {
        $('#comments').html(data);
        initComments();
        initVoting();
    });
}

function showAllImages(contentType, contentId) {
    var params = 'contentType=' + contentType + '&contentId=' + contentId.toString();
    $.get('/ajax/relatedImages.xhtml', params, function(data) {
        $('#relatedImagedHolder').html(data);
        $('#gallery a').lightBox();
        $('#firstGallery').click();
        
    });
    return false;
}



function closeModal() {
    $('#modalHolder').dialog('close');
}



function toggleComment(el) {
    var display = 'none';
    if (el.className == 'collapse') { el.className = 'expand'; } else { el.className = 'collapse';display = 'block'; }
    var children = el.parentNode.parentNode.parentNode.childNodes;
    for (i = 0; i < children.length; i++) {
        console.log(children[i].tagName);
        if (children[i].tagName == 'UL' || children[i].tagName == 'P') children[i].style.display = display;
    }
    return false;
}








function initComments() {
    $('.commentHolder').each(function() {
        parts = this.id.split('_');
        if (parts[3] == '0') this.innerHTML = "<a href=\"#\" onclick=\"return loadComment(this);\">Post a Comment</a>";
        else {
            this.innerHTML = "<a href=\"#\" class=\"reply\" onclick=\"return loadComment(this);\">[Reply]</a>";
            if (parts.length > 4) this.innerHTML += " <a href=\"/cp/editPost.xhtml?id=" + parts[3] + "\" class=\"reply\">[Edit]</a>";
        }
    });
}

function initVoting() {
    $('.voteHolder').each(function() {
        initVotingElement(this);
    });
}

function initListen() {
    //if ($.browser.mozilla || ($.browser.msie  && parseInt($.browser.version,10)<9) ) return;
    $('.listen').each(function() {
        initListenElement(this);
    });
}

function initListenElement(el) {
    parts = el.id.split('_');
    el.innerHTML = "<a href=\"javascript:playPassage(" + parts[1] + "," + parts[2] + ");\"><img src=\"/images/play.gif\" border=\"0\" alt=\"Listen\"></a> ";
}

function initVotingElement(el) {
    parts = el.id.split('_');
    if (document.all) votes = el.innerText; else votes = el.textContent;
    el.innerHTML = "<span class=\"vote\"><a href=\"javascript:vote('" + parts[1] + "'," + parts[2] + ",true)\"><img src=\"/images/voteup.png\" /></a><span class=\"inform\">" + votes + "</span><a href=\"javascript:vote('" + parts[1] + "'," + parts[2] + ",false)\"><img src=\"/images/votedown.png\" /></a></span>";
}

function verifyLoggedIn() {
    if ($('#email-field').length > 0) {
        alert('Please log in first');
        return false;
    }
    return true;
}

function recordOutboundClick(link, label) {
    _gat._getTrackerByName()._trackEvent('Outbound Click', link.href, label);
    //setTimeout("location.href='" + link.href + "';", 200);
    return false;
}


$(document).ready(function() {
    initVoting();
    initComments();
    initListen();
});



























function initPage() {
    clearFormFields({
        clearInputs: true,
        clearTextareas: true,
        passwordFieldText: true,
        addClassFocus: "focus",
        filterClass: "default"
    });
}
function clearFormFields(o) {
    if (o.clearInputs == null) o.clearInputs = true;
    if (o.clearTextareas == null) o.clearTextareas = true;
    if (o.passwordFieldText == null) o.passwordFieldText = false;
    if (o.addClassFocus == null) o.addClassFocus = false;
    if (!o.filterClass) o.filterClass = "default";
    if (o.clearInputs) {
        var inputs = document.getElementsByTagName("input");
        for (var i = 0; i < inputs.length; i++) {
            var process = false;
            switch (inputs[i].id) {
                case "email-field":
                case "password-field":
                case "search-field":
                    process = true;
                    break;
            }
            if (process || inputs[i].className == "text password-field") {
                if ((inputs[i].type == "text" || inputs[i].type == "password") && inputs[i].className.indexOf(o.filterClass) == -1) {
                    inputs[i].valueHtml = inputs[i].value;
                    inputs[i].onfocus = function() {
                        if (this.valueHtml == this.value) this.value = "";
                        if (this.fake) {
                            inputsSwap(this, this.previousSibling);
                            this.previousSibling.focus();
                        }
                        if (o.addClassFocus && !this.fake) {
                            this.className += " " + o.addClassFocus;
                            this.parentNode.className += " parent-" + o.addClassFocus;
                        }
                    }
                    inputs[i].onblur = function() {
                        if (this.value == "") {
                            this.value = this.valueHtml;
                            if (o.passwordFieldText && this.type == "password") inputsSwap(this, this.nextSibling);
                        }
                        if (o.addClassFocus) {
                            this.className = this.className.replace(o.addClassFocus, "");
                            this.parentNode.className = this.parentNode.className.replace("parent-" + o.addClassFocus, "");
                        }
                    }
                    if (o.passwordFieldText && inputs[i].type == "password") {
                        var fakeInput = document.createElement("input");
                        fakeInput.type = "text";
                        fakeInput.value = inputs[i].value;
                        fakeInput.className = inputs[i].className;
                        fakeInput.fake = true;
                        inputs[i].parentNode.insertBefore(fakeInput, inputs[i].nextSibling);
                        inputsSwap(inputs[i], null);
                    }
                }
            }
        }
    }
    function inputsSwap(el, el2) {
        if (el) el.style.display = "none";
        if (el2) el2.style.display = "inline";
    }
}
if (window.addEventListener)
    window.addEventListener("load", initPage, false);
else if (window.attachEvent)
    window.attachEvent("onload", initPage);






// browser detect script
browserDetect = {
    matchGroups: [
		[
			{ uaString: 'win', className: 'win' },
			{ uaString: 'mac', className: 'mac' },
			{ uaString: ['linux', 'x11'], className: 'linux' }
		],
		[
			{ uaString: 'msie', className: 'trident' },
			{ uaString: 'applewebkit', className: 'webkit' },
			{ uaString: 'gecko', className: 'gecko' },
			{ uaString: 'opera', className: 'presto' }
		],
		[
			{ uaString: 'msie 9.0', className: 'ie9' },
			{ uaString: 'msie 8.0', className: 'ie8' },
			{ uaString: 'msie 7.0', className: 'ie7' },
			{ uaString: 'msie 6.0', className: 'ie6' },
			{ uaString: 'firefox/2', className: 'ff2' },
			{ uaString: 'firefox/3', className: 'ff3' },
			{ uaString: 'firefox/4', className: 'ff4' },
			{ uaString: ['opera', 'version/11'], className: 'opera11' },
			{ uaString: ['opera', 'version/10'], className: 'opera10' },
			{ uaString: 'opera/9', className: 'opera9' },
			{ uaString: ['safari', 'version/3'], className: 'safari3' },
			{ uaString: ['safari', 'version/4'], className: 'safari4' },
			{ uaString: ['safari', 'version/5'], className: 'safari5' },
			{ uaString: 'chrome', className: 'chrome' },
			{ uaString: 'safari', className: 'safari2' },
			{ uaString: 'unknown', className: 'unknown' }
		]
	],
    init: function() {
        this.detect();
        return this;
    },
    addClass: function(className) {
        this.pageHolder = document.documentElement;
        document.documentElement.className += ' ' + className;
    },
    detect: function() {
        for (var i = 0, curGroup; i < this.matchGroups.length; i++) {
            curGroup = this.matchGroups[i];
            for (var j = 0, curItem; j < curGroup.length; j++) {
                curItem = curGroup[j];
                if (typeof curItem.uaString === 'string') {
                    if (this.uaMatch(curItem.uaString)) {
                        this.addClass(curItem.className);
                        break;
                    }
                } else {
                    for (var k = 0, allMatch = true; k < curItem.uaString.length; k++) {
                        if (!this.uaMatch(curItem.uaString[k])) {
                            allMatch = false;
                            break;
                        }
                    }
                    if (allMatch) {
                        this.addClass(curItem.className);
                        break;
                    }
                }
            }
        }
    },
    uaMatch: function(s) {
        if (!this.ua) {
            this.ua = navigator.userAgent.toLowerCase();
        }
        return this.ua.indexOf(s) != -1;
    }
}.init();
