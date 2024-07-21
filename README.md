# Projeto CarPlaceCharger

Este é o projeto CarPlaceCharger, um aplicativo para encontrar e gerenciar pontos de carregamento de veículos elétricos.

## Desenvolvedor
**Alan**
 **&**
**Gabriel**

## Descrição do Projeto

O CarPlaceCharger é um aplicativo Android que utiliza o Google Maps para mostrar pontos de carregamento e a localização do usuário. A aplicação inclui uma interface de mapa com funcionalidades de busca e filtragem de usuários.

## Alterações Recentes

1. **Adição da Funcionalidade de Mapa**
    - **Objetivo**: Adicionar um mapa interativo usando a biblioteca Google Maps.
    - **Mudanças**:
        - Implementação do componente `GoogleMap` usando a biblioteca `com.google.maps.android.compose`.
        - Configuração da câmera do mapa para a localização atual do usuário.
        - Adição de marcadores para exibir usuários no mapa.
        - Implementação de um botão flutuante para centralizar o mapa na localização atual.

2. **Integração com Firebase**
    - **Objetivo**: Obter e exibir usuários no mapa a partir do Firebase.
    - **Mudanças**:
        - Adição de listeners para receber dados de usuários do Firebase.
        - Aplicação de filtros para a exibição dos usuários no mapa com base em critérios de busca e filtro.

3. **Implementação da Bottom Sheet**
    - **Objetivo**: Adicionar uma bottom sheet para exibir informações adicionais e filtros de busca.
    - **Mudanças**:
        - Utilização do `BottomSheetScaffold` para criar a interface da bottom sheet.
        - Adição de controles de filtragem e pesquisa no bottom sheet.

4. **Permissões de Localização**
    - **Objetivo**: Gerenciar permissões para acesso à localização do usuário.
    - **Mudanças**:
        - Implementação de permissões para acessar a localização do dispositivo.
        - Atualização da localização do usuário em tempo real.

## Requisitos

- **Permissões**:
    - `android.permission.CAMERA`
    - `android.permission.ACCESS_FINE_LOCATION`
    - `android.permission.ACCESS_COARSE_LOCATION`

- **Dependências**:
    - `com.google.maps.android:maps-compose`
    - `com.google.accompanist:accompanist-permissions`
    - `com.google.firebase:firebase-database`

## Instruções de Uso

1. **Configuração do Mapa**:
    - Configure a chave da API do Google Maps no arquivo `AndroidManifest.xml`.

2. **Permissões**:
    - Certifique-se de que o aplicativo possui as permissões necessárias para acessar a localização.

3. **Uso do Bottom Sheet**:
    - Utilize a bottom sheet para pesquisar e filtrar os pontos de carregamento e usuários.


