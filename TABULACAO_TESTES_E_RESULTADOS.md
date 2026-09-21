# PLANO DE TESTES DE SOFTWARE (TABULACAO_TESTES_E_RESULTADOS.md)
** Curso: ** Qualificação profissional em Programador de Sistemas - Senac BA
** UC3: ** Realizar testes e manutenção do sistema de informação
** Projeto Integrador: ** Zurb
** Equipe: ** Adriele Blanco, Jeferson Conceição, Joilson Couto, Letícia Costa, Luiz Azevedo
** Data: 21.09.2026

---
## 1. ESCOPO DOS TESTES
Este documento descreve os cenários de teste aplicados ao sistema do Projeto Integrador.

### Ambientes e recursos utilizados:
- ** Linguagem/IDE: ** Java/VS Code
- ** Banco de dados: ** MySQL
- ** Controle de versão: ** Git e Github

---
## 2. MATRIZ DE CASO DE TESTES
| ID | Funcionalidade | Entrada / Dados de teste | Resultado Esperado | Status
| :---: | :--- | :--- | :--- | :---: |
| **CT-01** | Login | email: diamantenegro@gmail.com; Palavra chave: cachorro; senha: 0102; cpf: 01020300405 | O sistema deve cadastrar o usuário com sucesso e guardar suas informações no banco de dados. | APROVADO |
| **CT-02** | Login | email: diamantenegro@gmail.com; Palavra chave: cachorro; senha: 0102; cpf: 01020300405 | O sistema autentica as informações e permite a entrada do usuário na plataforma. | APROVADO |
| **CT-03** | Login incorreto | email: diamantenegro@gmail.com / cpf: 12345
senha: 3210 (Dados incorretos/inexistentes) | O sistema bloqueia o acesso e envia uma mensagem para o usuário: "email/cpf ou senha incorretos". | APROVADO |
| **CT-04** | Recuperar senha | email: diamantenegro@gmail.com / cpf: 12345
senha: 3210 (Usuário aciona fluxo de perda de senha) | O sistema dá a opção de recuperação de senha: "esqueci minha senha", onde o usuário consegue fazer a recuperação usando sua palavra chave. | APROVADO |

---
## 2. MATRIZ DE CASO DE TESTES
| Taxa de conformidade | Calculo | Resultado | 
| :---: | :--- | :---: | 
| Teste aprovado/teste realizado * 100 | (4/4) * 100 | 100 | 
