package org.fisco.bcos.channel.test.NUS;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class SmallBank extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50610c2e806100206000396000f300608060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630b488b371461007d5780630be8374d146100f05780633a51d24614610163578063870187eb146101e0578063901d706f14610253578063ca30543514610302575b600080fd5b34801561008957600080fd5b506100ee600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506103bb565b005b3480156100fc57600080fd5b50610161600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506104a7565b005b34801561016f57600080fd5b506101ca600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610689565b6040518082815260200191505060405180910390f35b3480156101ec57600080fd5b50610251600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610778565b005b34801561025f57600080fd5b50610300600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610864565b005b34801561030e57600080fd5b506103b9600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610a2c565b005b6000806000846040518082805190602001908083835b6020831015156103f657805182526020820191506020810190506020830392506103d1565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205491508290508082016000856040518082805190602001908083835b60208310151561046a5780518252602082019150602081019050602083039250610445565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208190555050505050565b60008060006001856040518082805190602001908083835b6020831015156104e457805182526020820191506020810190506020830392506104bf565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205492506000856040518082805190602001908083835b602083101515610552578051825260208201915060208101905060208303925061052d565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054915083905081830181101561060f576001818403036001866040518082805190602001908083835b6020831015156105d357805182526020820191506020810190506020830392506105ae565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902081905550610682565b8083036001866040518082805190602001908083835b60208310151561064a5780518252602082019150602081019050602083039250610625565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055505b5050505050565b600080600080846040518082805190602001908083835b6020831015156106c557805182526020820191506020810190506020830392506106a0565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205491506001846040518082805190602001908083835b602083101515610733578051825260208201915060208101905060208303925061070e565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054905080820192508292505050919050565b6000806001846040518082805190602001908083835b6020831015156107b3578051825260208201915060208101905060208303925061078e565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205491508290508082016001856040518082805190602001908083835b6020831015156108275780518252602082019150602081019050602083039250610802565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208190555050505050565b6000806000846040518082805190602001908083835b60208310151561089f578051825260208201915060208101905060208303925061087a565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205491506001836040518082805190602001908083835b60208310151561090d57805182526020820191506020810190506020830392506108e8565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054905060006001856040518082805190602001908083835b60208310151561097d5780518252602082019150602081019050602083039250610958565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055508082016000846040518082805190602001908083835b6020831015156109ef57805182526020820191506020810190506020830392506109ca565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208190555050505050565b60008060006001866040518082805190602001908083835b602083101515610a695780518252602082019150602081019050602083039250610a44565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205492506001856040518082805190602001908083835b602083101515610ad75780518252602082019150602081019050602083039250610ab2565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054915083905080830392508082019150826001876040518082805190602001908083835b602083101515610b535780518252602082019150602081019050602083039250610b2e565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902081905550816001866040518082805190602001908083835b602083101515610bc35780518252602082019150602081019050602083039250610b9e565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055505050505050505600a165627a7a72305820c2378cf2cfad78617110fe8219b4e01a1b5ab96205eae2cf6f9ee91cb4b4eec50029"};

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"uint256\"}],\"name\":\"updateSaving\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"uint256\"}],\"name\":\"writeCheck\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"}],\"name\":\"getBalance\",\"outputs\":[{\"name\":\"balance\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"uint256\"}],\"name\":\"updateBalance\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"string\"}],\"name\":\"almagate\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"string\"},{\"name\":\"arg2\",\"type\":\"uint256\"}],\"name\":\"sendPayment\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_UPDATESAVING = "updateSaving";

    public static final String FUNC_WRITECHECK = "writeCheck";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_UPDATEBALANCE = "updateBalance";

    public static final String FUNC_ALMAGATE = "almagate";

    public static final String FUNC_SENDPAYMENT = "sendPayment";

    @Deprecated
    protected SmallBank(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SmallBank(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SmallBank(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SmallBank(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public RemoteCall<TransactionReceipt> updateSaving(String arg0, BigInteger arg1) {
        final Function function = new Function(
                FUNC_UPDATESAVING, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateSaving(String arg0, BigInteger arg1, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATESAVING, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String updateSavingSeq(String arg0, BigInteger arg1) {
        final Function function = new Function(
                FUNC_UPDATESAVING, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getUpdateSavingInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UPDATESAVING, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> writeCheck(String arg0, BigInteger arg1) {
        final Function function = new Function(
                FUNC_WRITECHECK, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void writeCheck(String arg0, BigInteger arg1, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_WRITECHECK, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String writeCheckSeq(String arg0, BigInteger arg1) {
        final Function function = new Function(
                FUNC_WRITECHECK, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getWriteCheckInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_WRITECHECK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public RemoteCall<BigInteger> getBalance(String arg0) {
        final Function function = new Function(FUNC_GETBALANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public String getBalanceSeq(String arg0) {
        final Function function = new Function(
                FUNC_GETBALANCE,
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> updateBalance(String arg0, BigInteger arg1) {
        final Function function = new Function(
                FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateBalance(String arg0, BigInteger arg1, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String updateBalanceSeq(String arg0, BigInteger arg1) {
        final Function function = new Function(
                FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getUpdateBalanceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UPDATEBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> almagate(String arg0, String arg1) {
        final Function function = new Function(
                FUNC_ALMAGATE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void almagate(String arg0, String arg1, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ALMAGATE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String almagateSeq(String arg0, String arg1) {
        final Function function = new Function(
                FUNC_ALMAGATE, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg1)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, String> getAlmagateInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ALMAGATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> sendPayment(String arg0, String arg1, BigInteger arg2) {
        final Function function = new Function(
                FUNC_SENDPAYMENT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg1), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg2)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void sendPayment(String arg0, String arg1, BigInteger arg2, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SENDPAYMENT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg1), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg2)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String sendPaymentSeq(String arg0, String arg1, BigInteger arg2) {
        final Function function = new Function(
                FUNC_SENDPAYMENT, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg0), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(arg1), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(arg2)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple3<String, String, BigInteger> getSendPaymentInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SENDPAYMENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple3<String, String, BigInteger>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue()
                );
    }

    @Deprecated
    public static SmallBank load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmallBank(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SmallBank load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SmallBank(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SmallBank load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SmallBank(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SmallBank load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SmallBank(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SmallBank> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SmallBank.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SmallBank> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SmallBank.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SmallBank> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SmallBank.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SmallBank> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SmallBank.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
