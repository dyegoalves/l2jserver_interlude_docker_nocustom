#!/bin/bash

# Executa o script SQL
echo "Executando o script SQL..."

cd /var/lib/mysql/bkp/

mysql l2jteste <database.sql

# Verifica se o comando anterior foi bem-sucedido
if [ $? -eq 0 ]; then
    echo "Script SQL executado com sucesso!"
else
    echo "Erro ao executar o script SQL."
    exit 1 # Caso haja erro, finalize com erro
fi

# Mantenha o contêiner em execução após o script SQL ser executado com sucesso
echo "Aguardando novas conexões ou comandos..."

# O comando abaixo impede que o contêiner pare. Pode ser substituído por qualquer comando de "manutenção" ou "loop infinito" que mantenha o contêiner ativo
tail -f /dev/null
