package org.fisco.bcos.channel.test.NUS;

import java.math.BigInteger;
import java.util.Arrays;
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
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
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
    public static final String[] BINARY_ARRAY = {"60806040526110066000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034801561005257600080fd5b50611384806100626000396000f3006080604052600436106100a4576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680630b488b37146100a95780630be8374d1461011c57806334a18dda1461018f5780633a51d2461461020257806379fa913f1461027f578063870187eb146102e8578063901d706f1461035b578063bca926af1461040a578063ca30543514610421578063d39f70bc146104da575b600080fd5b3480156100b557600080fd5b5061011a600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506104f1565b005b34801561012857600080fd5b5061018d600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506105dd565b005b34801561019b57600080fd5b50610200600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001909291905050506107bf565b005b34801561020e57600080fd5b50610269600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610930565b6040518082815260200191505060405180910390f35b34801561028b57600080fd5b506102e6600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610a20565b005b3480156102f457600080fd5b50610359600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610b88565b005b34801561036757600080fd5b50610408600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610c74565b005b34801561041657600080fd5b5061041f610e3c565b005b34801561042d57600080fd5b506104d8600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610fe5565b005b3480156104e657600080fd5b506104ef6111bb565b005b6000806001846040518082805190602001908083835b60208310151561052c5780518252602082019150602081019050602083039250610507565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205491508290508082016001856040518082805190602001908083835b6020831015156105a0578051825260208201915060208101905060208303925061057b565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208190555050505050565b60008060006002856040518082805190602001908083835b60208310151561061a57805182526020820191506020810190506020830392506105f5565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205492506001856040518082805190602001908083835b6020831015156106885780518252602082019150602081019050602083039250610663565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020549150839050818301811015610745576001818403036002866040518082805190602001908083835b60208310151561070957805182526020820191506020810190506020830392506106e4565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055506107b8565b8083036002866040518082805190602001908083835b602083101515610780578051825260208201915060208101905060208303925061075b565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055505b5050505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16630553904e3084846040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b838110156108a2578082015181840152602081019050610887565b50505050905090810190601f1680156108cf5780820380516001836020036101000a031916815260200191505b50945050505050602060405180830381600087803b1580156108f057600080fd5b505af1158015610904573d6000803e3d6000fd5b505050506040513d602081101561091a57600080fd5b8101908080519060200190929190505050505050565b60008060006001846040518082805190602001908083835b60208310151561096d5780518252602082019150602081019050602083039250610948565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205491506002846040518082805190602001908083835b6020831015156109db57805182526020820191506020810190506020830392506109b6565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054905080820192508292505050919050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166311e3f2af30836040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610afc578082015181840152602081019050610ae1565b50505050905090810190601f168015610b295780820380516001836020036101000a031916815260200191505b509350505050602060405180830381600087803b158015610b4957600080fd5b505af1158015610b5d573d6000803e3d6000fd5b505050506040513d6020811015610b7357600080fd5b81019080805190602001909291905050505050565b6000806002846040518082805190602001908083835b602083101515610bc35780518252602082019150602081019050602083039250610b9e565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205491508290508082016002856040518082805190602001908083835b602083101515610c375780518252602082019150602081019050602083039250610c12565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208190555050505050565b6000806001846040518082805190602001908083835b602083101515610caf5780518252602082019150602081019050602083039250610c8a565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205491506002836040518082805190602001908083835b602083101515610d1d5780518252602082019150602081019050602083039250610cf8565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054905060006002856040518082805190602001908083835b602083101515610d8d5780518252602082019150602081019050602083039250610d68565b6001836020036101000a0380198251168184511680821785525050505050509050019150509081526020016040518091039020819055508082016001846040518082805190602001908083835b602083101515610dff5780518252602082019150602081019050602083039250610dda565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208190555050505050565b610e7c6040805190810160405280601781526020017f616c6d616761746528737472696e672c737472696e672900000000000000000081525060026107bf565b610ebc6040805190810160405280601281526020017f67657442616c616e636528737472696e6729000000000000000000000000000081525060016107bf565b610efc6040805190810160405280601d81526020017f75706461746542616c616e636528737472696e672c75696e743235362900000081525060016107bf565b610f3c6040805190810160405280601c81526020017f757064617465536176696e6728737472696e672c75696e74323536290000000081525060016107bf565b610fa3606060405190810160405280602281526020017f73656e645061796d656e7428737472696e672c737472696e672c75696e74323581526020017f362900000000000000000000000000000000000000000000000000000000000081525060","026107bf565b610fe36040805190810160405280601a81526020017f7772697465436865636b28737472696e672c75696e743235362900000000000081525060016107bf565b565b60008060006002866040518082805190602001908083835b6020831015156110225780518252602082019150602081019050602083039250610ffd565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390205492506002856040518082805190602001908083835b602083101515611090578051825260208201915060208101905060208303925061106b565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902054915083905080830392508082019150826002876040518082805190602001908083835b60208310151561110c57805182526020820191506020810190506020830392506110e7565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902081905550816002866040518082805190602001908083835b60208310151561117c5780518252602082019150602081019050602083039250611157565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902081905550505050505050565b6111f96040805190810160405280601781526020017f616c6d616761746528737472696e672c737472696e6729000000000000000000815250610a20565b6112376040805190810160405280601281526020017f67657442616c616e636528737472696e67290000000000000000000000000000815250610a20565b6112756040805190810160405280601d81526020017f75706461746542616c616e636528737472696e672c75696e7432353629000000815250610a20565b6112b36040805190810160405280601c81526020017f757064617465536176696e6728737472696e672c75696e743235362900000000815250610a20565b611318606060405190810160405280602281526020017f73656e645061796d656e7428737472696e672c737472696e672c75696e74323581526020017f3629000000000000000000000000000000000000000000000000000000000000815250610a20565b6113566040805190810160405280601a81526020017f7772697465436865636b28737472696e672c75696e7432353629000000000000815250610a20565b5600a165627a7a7230582082c68850614a988568a9865779e89c6f13451c761c60b37dc27db89300ceb9650029"};

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"uint256\"}],\"name\":\"updateSaving\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"uint256\"}],\"name\":\"writeCheck\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"functionName\",\"type\":\"string\"},{\"name\":\"criticalSize\",\"type\":\"uint256\"}],\"name\":\"registerParallelFunction\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"}],\"name\":\"getBalance\",\"outputs\":[{\"name\":\"balance\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"functionName\",\"type\":\"string\"}],\"name\":\"unregisterParallelFunction\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"uint256\"}],\"name\":\"updateBalance\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"string\"}],\"name\":\"almagate\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"enableParallel\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"arg0\",\"type\":\"string\"},{\"name\":\"arg1\",\"type\":\"string\"},{\"name\":\"arg2\",\"type\":\"uint256\"}],\"name\":\"sendPayment\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"disableParallel\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_UPDATESAVING = "updateSaving";

    public static final String FUNC_WRITECHECK = "writeCheck";

    public static final String FUNC_REGISTERPARALLELFUNCTION = "registerParallelFunction";

    public static final String FUNC_GETBALANCE = "getBalance";

    public static final String FUNC_UNREGISTERPARALLELFUNCTION = "unregisterParallelFunction";

    public static final String FUNC_UPDATEBALANCE = "updateBalance";

    public static final String FUNC_ALMAGATE = "almagate";

    public static final String FUNC_ENABLEPARALLEL = "enableParallel";

    public static final String FUNC_SENDPAYMENT = "sendPayment";

    public static final String FUNC_DISABLEPARALLEL = "disableParallel";

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

    public RemoteCall<TransactionReceipt> registerParallelFunction(String functionName, BigInteger criticalSize) {
        final Function function = new Function(
                FUNC_REGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(criticalSize)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void registerParallelFunction(String functionName, BigInteger criticalSize, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(criticalSize)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String registerParallelFunctionSeq(String functionName, BigInteger criticalSize) {
        final Function function = new Function(
                FUNC_REGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(criticalSize)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getRegisterParallelFunctionInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REGISTERPARALLELFUNCTION, 
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

    public RemoteCall<TransactionReceipt> unregisterParallelFunction(String functionName) {
        final Function function = new Function(
                FUNC_UNREGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void unregisterParallelFunction(String functionName, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UNREGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String unregisterParallelFunctionSeq(String functionName) {
        final Function function = new Function(
                FUNC_UNREGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(functionName)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getUnregisterParallelFunctionInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UNREGISTERPARALLELFUNCTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
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

    public RemoteCall<TransactionReceipt> enableParallel() {
        final Function function = new Function(
                FUNC_ENABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void enableParallel(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ENABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String enableParallelSeq() {
        final Function function = new Function(
                FUNC_ENABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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

    public RemoteCall<TransactionReceipt> disableParallel() {
        final Function function = new Function(
                FUNC_DISABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void disableParallel(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_DISABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String disableParallelSeq() {
        final Function function = new Function(
                FUNC_DISABLEPARALLEL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
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
