import { describe, it, expect, beforeAll, afterEach, afterAll } from 'vitest';
import { server } from './server.js';
import { fastify } from './index.js';
import { http, HttpResponse } from 'msw'

beforeAll(() => server.listen({ onUnhandledRequest: 'error' }));

afterEach(() => server.resetHandlers());

afterAll(() => server.close());

describe('POST /summarize', () => {
    it('deve chamar a IA e retornar o resumo processado', async () => {
        const response = await fastify.inject({
            method: 'POST',
            url: '/summarize',
            payload: { text: "Texto longo que precisa de resumo..." }
        });

        expect(response.statusCode).toBe(200);
        expect(response.json()).toEqual({
            success: true,
            summary: "Este é um resumo gerado pelo MSW na integração."
        });
    });
    it('deve retornar erro 500 quando a API da IA falhar', async () => {
        server.use(
            http.post('https://api.fake-ai.com/v1/generate', () => {
                return HttpResponse.error();
            })
        );
        const response = await fastify.inject({
            method: 'POST',
            url: '/summarize',
            payload: { text: "Texto longo..." }
        });

        expect(response.statusCode).toBe(500);
        expect(response.json()).toEqual({
            error: 'Erro ao se comunicar com o serviço de IA'
        });
    });
});