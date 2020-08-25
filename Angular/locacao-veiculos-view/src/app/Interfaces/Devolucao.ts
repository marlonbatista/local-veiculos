export default interface Devolucao {
    cod_aluguel: number;
    cod_cliente: number;
    cod_veiculo: number;
    nome_cliente: string;
    nome_veiculo: string;
    data_locacao: Date;
    data_devolucao: Date;
    valor_total: number;
    status: boolean;
}
