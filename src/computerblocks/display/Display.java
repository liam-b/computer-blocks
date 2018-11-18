package computerblocks.display;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import computerblocks.position.*;
// import computerblocks.player.io.Keyboard;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.awt.*;
import java.awt.FontMetrics;

import java.awt.geom.*;

public class Display {
  private long window;
  public int width = 1280;
  public int height = 720;

  public boolean windowShouldClose = false;

  public Display() {
    GLFWErrorCallback.createPrint(System.err).set();
    if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

    window = glfwCreateWindow(width, height, "computer-blocks", NULL, NULL);
    if (window == NULL)throw new RuntimeException("Failed to create the GLFW window");
    glfwSetWindowSizeLimits(window, 640, 480, GLFW_DONT_CARE, GLFW_DONT_CARE);

    IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
    IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
    glfwGetWindowSize(window, widthBuffer, heightBuffer);
    width = widthBuffer.get(0);
    height = heightBuffer.get(0);

    glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(window, true);
      if (key == GLFW_KEY_L && action == GLFW_PRESS) glfwSetWindowSize(window, 100, 100);
		});

    glfwSetWindowSizeCallback(window, this::onResize);

    GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    glfwSetWindowPos(window,
      (vidmode.width() - height) / 2,
      (vidmode.height() - width) / 2
    );

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);

    GL.createCapabilities();
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glDisable(GL_DEPTH_TEST);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

    setup();
  }

  public void setup() {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, width, height, 0, 1, 0);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
  }

  public void clear() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    windowShouldClose = glfwWindowShouldClose(window);
  }

  public void draw() {
    glfwSwapBuffers(window);
		glfwPollEvents();
  }

  public void destroy() {
    glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
  }

  public void rect(double x, double y, double width, double height) {
    glBegin(GL_QUADS);
      glColor3d(1.0, 0.439, 0.439);
      glVertex2d(x, y + height);
      glVertex2d(x + width, y + height);
      glVertex2d(x + width, y);
      glVertex2d(x, y);
    glEnd();
  }

  public void onResize(long win, int width, int height) {
    this.width = width;
    this.height = height;
    setup();
  }

  public void color(Color color) {
    glColor3d((double)color.data.getRed() / 255.0, (double)color.data.getGreen() / 255.0, (double)color.data.getBlue() / 255.0);
  }

  public void rect(RealPosition position, double width, double height) {
    rect(position.x, position.y, width, height);
  }
}