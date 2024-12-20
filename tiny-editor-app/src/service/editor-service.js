import axios from "axios";

async function sendEvent(eventName, text = null, selected = null) {
    const url = 'http://localhost:8080/event';
    const body = {
        name: eventName,
        text: text,
        selected: selected ? { beginIndex: selected.beginIndex, endIndex: selected.endIndex } : null
    };

    try {
        const response = await axios.post(url, body, {
            headers: {
                'Content-Type': 'application/json'
            }
        });
        console.log('Response:', response.data);
        return response.data;
    } catch (error) {
        console.error('Error sending event:', error);
        throw error;
    }
}

export async function insertText(text) {
    return sendEvent('insert', text);
}

export async function moveSelection(beginIndex, endIndex) {
    return sendEvent('moveSelection', null, {beginIndex, endIndex});
}

export async function copy() {
    return sendEvent('copy');
}

export async function cut() {
    return sendEvent('cut');
}

export async function deleteText() {
    return sendEvent('delete');
}

export async function paste() {
    return sendEvent('paste');
}

export async function startRecord() {
    return sendEvent('startRecord');
}

export async function stopRecord() {
    return sendEvent('stopRecord');
}

export async function replayRecord() {
    return sendEvent('replayRecord');
}