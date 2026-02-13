import { http, HttpResponse } from 'msw';

export const handlers = [
    http.post('https://api.fake-ai.com/v1/generate', () => {
        return HttpResponse.json({
            generated_text: "Este é um resumo gerado pelo MSW na integração."
        });
    })
];