from sklearn import datasets
from sklearn.cross_validation import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import  Perceptron
from matplotlib.colors import ListedColormap
import  matplotlib.pyplot as plt
import numpy as np

iris=datasets.load_iris()
x = iris.data[:,[2,3]]
y = iris.target

scaler = StandardScaler()
scaler.fit(x)
x=scaler.transform(x)

xtrain, xtest, ytrain, ytest = train_test_split(x,y,test_size=0.3,random_state=0)
neuron = Perceptron (n_iter=500,eta0=0.01,random_state=0)
neuron.fit (xtrain,ytrain)

predic = neuron.predict(xtest)

print(predic)
print("woooo")
print(ytest)
print (len(xtrain))
