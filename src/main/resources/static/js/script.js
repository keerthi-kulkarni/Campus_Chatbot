const userId = 'user-' + Math.random().toString(36).substr(2, 9);

async function sendMessage() {
    const userInput = document.getElementById('userInput').value;
    if (!userInput) return;

    const chatBox = document.getElementById('chatBox');
    const userMessage = document.createElement('div');
    userMessage.className = 'message user';
    userMessage.textContent = userInput;
    chatBox.appendChild(userMessage);

    try {
        const response = await fetch('http://localhost:8080/api/chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ message: userInput, userId: userId }),
        });
        const data = await response.json();

        const botMessage = document.createElement('div');
        botMessage.className = 'message bot';
        botMessage.textContent = data.response;
        chatBox.appendChild(botMessage);
    } catch (error) {
        console.error('Error:', error);
        const botMessage = document.createElement('div');
        botMessage.className = 'message bot';
        botMessage.textContent = 'Oops, something went wrong. Please try again!';
        chatBox.appendChild(botMessage);
    }

    document.getElementById('userInput').value = '';
    chatBox.scrollTop = chatBox.scrollHeight;
}