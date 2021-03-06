package org.thunlp.hadoop;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * 这个类可以用来保存稀疏实数向量，它保证向量中元素下标的顺序在读写过程中不变
 * 但是并不会对它们排序。在给SparseVectorWritable赋值的时候，应该先setSize()， 然后getIndex,
 * getValue，设置数组中的值。
 * 
 * @author adam
 *
 */
public class SparseVectorWritable implements Writable {
	public int[] indexes;
	public double[] values;
	int size;

	public SparseVectorWritable() {
		init(10);
	}

	public SparseVectorWritable(int size) {
		init(size);
	}

	private void init(int size) {
		indexes = new int[size];
		values = new double[size];
		size = 0;
	}

	public void setSize(int size) {
		this.size = size;
		if (size < indexes.length) {
			return;
		}

		indexes = new int[size];
		values = new double[size];
	}

	public int size() {
		return size;
	}

	public int[] getIndex() {
		return indexes;
	}

	public double[] getValues() {
		return values;
	}

	public void readFields(DataInput in) throws IOException {
		size = in.readInt();
		setSize(size);
		for (int i = 0; i < size; i++) {
			indexes[i] = in.readInt();
			values[i] = in.readDouble();
		}
	}

	public void write(DataOutput out) throws IOException {
		out.writeInt(size);
		for (int i = 0; i < size; i++) {
			out.writeInt(indexes[i]);
			out.writeDouble(values[i]);
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(indexes[i]);
			sb.append(":");
			sb.append(values[i]);
			sb.append("/");
		}
		return sb.toString();
	}

}
