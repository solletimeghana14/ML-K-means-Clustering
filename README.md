K-means clustering for image compression.

The algorithm is implemented in Java
K-means clustering for different values of K(2, 5, 10, 15, 20) to compress the image

Run Command for KMeans(Java File)
javac KMeans.java
java KMeans <input-image> <k> <output-image>

Where input-image is the name of the desired image in the same directory and k is the number of cluster centers.
For example to excute with Koala with k=2 and the compressed image name is Koala-compressed 

java KMeans Koala.jpg 2 Koala-compressed.jpg
