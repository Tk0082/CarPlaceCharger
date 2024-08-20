package com.beTrend.CarPlaceCharger.presentation.profile

import com.beTrend.CarPlaceCharger.CarPlaceCharger.R

object DataConector {
    val conectorList = listOf(
        Conector(
            id = 1,
            img = R.drawable.tesla_ac_dc,
            name = "Tesla",
            desc = "O conector de carregamento Tesla ou North American Charging Standard (NACS) para carros elétricos é um padrão de carregamento que foi desenvolvido pela empresa americana Tesla.\n" +
                    "\n" +
                    "Atualmente, este é um dos principais conectores de carregamento para veículos elétricos nos Estados Unidos. Alguns exemplos de veículos que utilizam este tipo de conector são: Tesla Model S, Tesla Model X e entre outros. \n" +
                    "\n" +
                    "No ano de 2022 a Tesla anunciou a abertura para uso deste tipo de conector a outros fabricantes promovendo maior interoperabilidade e facilidade na transição para mobilidade elétrica.\n" +
                    "\n" +
                    "Este conector é amplamente utilizado para carregamento de corrente alternada. Também permite o carregamento rápido em corrente contínua (CC) utilizando o mesmo carregador.\n" +
                    "\n" +
                    "A Tesla oferece adaptadores para outros tipos de conectores como o Tipo 1 e o CHAdeMO. Na Europa a empresa americana passou a adotar o conector Tipo 2.\n" +
                    "\n" +
                    "Até a data desta publicação este conector pode suportar uma potência de carregamento de até 250 kW. Contudo, na configuração mais comum a potência de carregamento residencial é de 11.5 kW."
        ),
        Conector(
            id = 2,
            img = R.drawable.type_1,
            name = "Tipo 1 (SAE J1772)",
            desc = "O conector de carregamento para carros elétricos do Tipo 1, também conhecido como SAE J1772, é um padrão norte americano especificado pela SAE (Sociedade de Engenheiros da Mobilidade). \nEste é um dos primeiros e um dos mais utilizados conectores de carregamento para veículos eletrificados.\n" +
                    "\n" +
                    "Um exemplo de veículo que utiliza este tipo de conector é o Nissan Leaf até o ano modelo 2022. Desde a sua adoção inicial em 2001, este conector já passou por várias atualizações.\n" +
                    "\n" +
                    "Este conector é amplamente utilizado para carregamento de corrente alternada monofásica em: espaços comerciais, residenciais, estações de carregamento públicas, entre outros.\n" +
                    "\n" +
                    "O conector SAE J1772 geralmente inclui uma variante chamada CCS (Combined Charging System). É adicionado mais dois pinos ao conector, tornando um sistema combinado de carregamento.\n" +
                    "\n" +
                    "O CCS é uma evolução do conector Tipo 1 que foi criado para permitir o carregamento rápido de veículos elétricos com corrente contínua (CC) em conjunto ao carregamento de corrente alternada (CA).\n" +
                    "\n" +
                    "Em sua última revisão, até a data desta publicação, a norma J1772 especifica que este conector pode suportar um carregamento AC monofásico em uma ampla faixa de potência. \nAbaixo temos as especificações gerais de potência de carregamento segundo a norma."
        ),
        Conector(
            id = 3,
            img = R.drawable.type_2,
            name = "Tipo 2 (IEC 62196)",
            desc = "O conector de carregamento para carros elétricos do Tipo 2, é um padrão Europeu especificado pela IEC (Comissão Eletrotécnica Internacional).\n" +
                    "\n" +
                    "Atualmente, este é um dos principais conectores de carregamento para veículos eletrificados na Europa e em outras partes do mundo. Alguns exemplos de veículos que utilizam este tipo de conector são: Volvo XC40 e o Renault Zoe.\n" +
                    "\n" +
                    "Este conector é amplamente utilizado para carregamento de corrente alternada monofásica e trifásica. Também pode apresentar a variante CCS (Combined Charging System), permitindo o carregamento rápido em corrente contínua (CC).\n" +
                    "\n" +
                    "Em seu projeto original, para carregar os veículos elétricos, este conector trabalha com uma tensão operacional de 230V monofásica e 400V trifásica, com uma corrente máxima de 32A. Com isso, a potência de carregamento pode ser \nde 7.3 kW para corrente alternada (CA) monofásica, e de 22 kW para corrente alternada (CA) trifásica.\n" +
                    "\n" +
                    "Contudo, de acordo com a norma IEC 62196, este tipo de conector pode ter uma tensão operacional de até 480V e não pode ultrapassar o valor de corrente nominal de 63A trifásica ou 70A monofásica.\n" +
                    "\n" +
                    "Para o conector Tipo 2 CCS, assim como o conector do Tipo 1, ele pode suportar uma potência de carregamento de até 400 kW."
        ),
        Conector(
            id = 4,
            img = R.drawable.css2_dc,
            name = "GB/T 20234",
            desc = "O conector de carregamento para carros elétricos GB/T é um padrão de carregamento que foi desenvolvido na China e se tornou um padrão reconhecido nacionalmente e em outras regiões do mundo.\n" +
                    "\n" +
                    "Um exemplo de veículo que utiliza este tipo de conector é o JAC e-JS1.\n" +
                    "\n" +
                    "O conector GB/T se assemelha ao conector Tipo 2, contudo possui configurações diferentes (não são compatíveis fisicamente). Enquanto o conector do Tipo 2 utiliza conector da estação de carga fêmea \ne a tomada no veículo macho, o GB/T especifica um conector macho para a estação de carregamento e a tomada no veículo fêmea.\n" +
                    "\n" +
                    "Este conector é amplamente utilizado para carregamento de corrente alternada (CA) monofásica e trifásica, embora exista a variação deste padrão para carregamento rápido em corrente contínua (CC). \nOs conectores de corrente alternada (CA) e corrente contínua (CC) são separados, ou seja, não são combinados.\n" +
                    "\n" +
                    "Este conector pode suportar uma potência de carregamento de até 250 kW. Contudo, na configuração mais comum a potência de carregamento residencial pode ser de 2.5 kW (monofásica) a 27.7 kW (trifásica)."
        ),
        Conector(
            id = 5,
            img = R.drawable.chademo,
            name = "CHAdeMO",
            desc = "O conector de carregamento CHAdeMO para carros elétricos é um padrão de carregamento rápido que foi desenvolvido no Japão e que tornou-se um padrão globalmente reconhecido. O nome se trata de uma abreviatura de “Charge de Move”.\n" +
                    "\n" +
                    "Atualmente, este é um dos principais conectores de carregamento para veículos elétricos no Japão e concorre com o Combined Charging System (CCS) aplicado aos conectores Tipo 1 e Tipo 2. Um exemplo de veículo que utiliza este tipo de conector é o Nissan Leaf.\n" +
                    "\n" +
                    "Este conector é utilizado para carregamento rápido em corrente contínua (CC). Até a data de publicação desta matéria, o CHAdeMO permite trabalhar com uma tensão operacional de 1 kV e 400 A, o que gera uma potência de carregamento de até 400 kW.\n" +
                    "\n" +
                    "Contudo, já existem pesquisas de desenvolvimento de um carregador ultra rápido capaz de carregar até 900 kW."
        ),
        Conector(
            id = 6,
            img = R.drawable.outros_modelos,
            name = "Outros Carregadores",
            desc = "Ainda há outros tipos de plugues que estão ganhando espaço como o de três pinos e os específicos industriais.\n" +
                    "Estes conectores são muito comuns em carregadores portáteis e geralmente utilizados por proprietários de veículos elétricos para carregar o carro na tomada de casa ao longo da noite ou em emergências. \n" +
                    "\n" +
                    "O conector padrão de 3 pinos suporta cargas até 3,7 kW devido ao limite da corrente ser de no máximo 16 A. Completar a bateria utilizando um equipamento com este plugue pode levar de 12 a 24 horas. \n" +
                    "\n" +
                    "Já o conector tipo industrial consegue carregar o veículo em potências maiores. O modelo de três pinos fornece energia em até 7,4 kW (220 V,32 A), e a versão de 5 pinos carrega em uma potência de até 22 kW (380 V, 32 A)."
        )
    )
}