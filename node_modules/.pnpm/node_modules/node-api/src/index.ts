import Fastify from 'fastify';
import axios from 'axios';

export const fastify = Fastify({ logger: true });

fastify.post('/summarize', async (request, reply) => {
    const { text } = request.body as { text: string };

    try {
        const iaResponse = await axios.post('https://api.fake-ai.com/v1/generate', {
            prompt: `Faça um resumo curto do seguinte texto: ${text}`
        });
        return {
            success: true,
            summary: iaResponse.data.generated_text
        };

    } catch (error) {
        fastify.log.error(error);
        return reply.status(500).send({ error: 'Erro ao se comunicar com o serviço de IA' });
    }
});

fastify.listen({ port: 3000 }, (err) => {
    if (err) {
        fastify.log.error(err);
        process.exit(1);
    }
    console.log('Servidor Node rodando na porta 3000');
});