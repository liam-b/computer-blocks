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
import computerblocks.player.*;
import computerblocks.player.io.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;
import java.awt.*;
import java.awt.FontMetrics;

import java.awt.geom.*;

public class Display {
  private long window;
  public int width;
  public int height;
  public String name;

  public boolean windowShouldClose = false;

  public Display(int width, int height, String name) {
    this.width = width;
    this.height = height;
    this.name = name;

    GLFWErrorCallback.createPrint(System.err).set();
    if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");

    glfwDefaultWindowHints();
    glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

    window = glfwCreateWindow(width, height, name, NULL, NULL);
    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window");
    glfwSetWindowSizeLimits(window, 640, 480, GLFW_DONT_CARE, GLFW_DONT_CARE);

    glfwSetWindowSizeCallback(window, this::resize);

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
    resize(window, width, height);
  }

  private void setup() {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, width, height, 0, 1, 0);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
  }

  private void resize(long win, int width, int height) {
    this.width = width;
    this.height = height;
    setup();
  }

  public void clear() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    windowShouldClose = glfwWindowShouldClose(window); // glfwSetWindowCloseCallback(window, window_close_callback); FIXME: USE THIS INSTEAD
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

  public void bindCallbacks(Keyboard keyboard, Mouse mouse) {
    glfwSetKeyCallback(window, keyboard::callback);

    glfwSetCursorPosCallback(window, mouse::positionCallback);
    glfwSetMouseButtonCallback(window, mouse::buttonCallback);
  }

  public void rect(double x, double y, double width, double height) {
    glBegin(GL_QUADS);
      glVertex2d(x, y + height);
      glVertex2d(x + width, y + height);
      glVertex2d(x + width, y);
      glVertex2d(x, y);
    glEnd();
  }

  public void rect(RealPosition position, double width, double height) {
    rect(position.x, position.y, width, height);
  }

  public void color(double r, double g, double b) {
    glColor3d(r / 255.0, g / 255.0, b / 255.0);
  }

  public void color(Color color) {
    color((double)color.data.getRed(), (double)color.data.getGreen(), (double)color.data.getBlue());
  }
}