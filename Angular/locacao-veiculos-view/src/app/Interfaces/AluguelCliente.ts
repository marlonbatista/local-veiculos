export default interface AluguelCliente {
    cod_aluguel: number;
    cod_cliente: number;
    cod_veiculo: number;
    data_locacao: Date;
    data_devolucao: Date;
    nome_cliente: string;
    valor_total: number;
    status: boolean;
}
