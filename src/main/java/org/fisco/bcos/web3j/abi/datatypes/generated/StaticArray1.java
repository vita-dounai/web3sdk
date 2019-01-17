package org.fisco.bcos.web3j.abi.datatypes.generated;

import org.fisco.bcos.web3j.abi.datatypes.StaticArray;
import org.fisco.bcos.web3j.abi.datatypes.Type;

import java.util.List;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use AbiTypesGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class StaticArray1<T extends Type> extends StaticArray<T> {
    public StaticArray1(List<T> values) {
        super(1, values);
    }

    @SafeVarargs
    public StaticArray1(T... values) {
        super(1, values);
    }
}