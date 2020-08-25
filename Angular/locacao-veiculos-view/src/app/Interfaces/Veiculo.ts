import { EnumCombustivel } from '../Enums/EnumCombustivel';

export interface Veiculo {
    cod_veiculo: number;
    ano: number;
    combustivel: EnumCombustivel;
    marca: string;
    modelo: number;
    nome: string;
    valor_diaria: number;
    status: boolean;
}
